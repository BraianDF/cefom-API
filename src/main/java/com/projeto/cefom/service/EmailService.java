package com.projeto.cefom.service;

import com.projeto.cefom.dto.request.EmailAtualizarRequestDTO;
import com.projeto.cefom.dto.response.EmailListarResponseDTO;
import com.projeto.cefom.dto.response.EmailResponseDTO;
import com.projeto.cefom.enums.TitularContato;
import com.projeto.cefom.exceptions.RecursoNaoEncontradoException;
import com.projeto.cefom.exceptions.RegraNegocioException;
import com.projeto.cefom.mapper.EmailMapper;
import com.projeto.cefom.model.Adolescente;
import com.projeto.cefom.model.Email;
import com.projeto.cefom.model.Empresa;
import com.projeto.cefom.repository.EmpresaRepository;
import com.projeto.cefom.repository.EmailRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;

@Service
public class EmailService {

    public final EmailRepository emailRepository;
    private final AdolescenteService adolescenteService;
    private final EmailMapper emailMapper;
    private final EmpresaRepository empresaRepository;

    public EmailService(EmailRepository emailRepository, AdolescenteService adolescenteService, EmailMapper emailMapper, EmpresaRepository empresaRepository) {
        this.emailRepository = emailRepository;
        this.adolescenteService = adolescenteService;
        this.emailMapper = emailMapper;
        this.empresaRepository = empresaRepository;
    }

    @Transactional
    public EmailResponseDTO atualizar(Integer idAdolescente, EmailAtualizarRequestDTO dto) {
        LocalDate dataModificacao = dto.dataModificacao();
        if(dataModificacao == null) {
            dataModificacao = LocalDate.now();
        }

        //Verifica se o adolescente existe e retorna ele
        Adolescente adolescente = adolescenteService.buscarAdolescente(idAdolescente);

        //Atualiza
        atualizarEmail(dto.emailAdolescente(), adolescente, dataModificacao);

        //Salva o adolescente no banco
        Adolescente adolescenteSalvo = adolescenteService.salvar(adolescente);

        //Retorna o ResponseDTO
        return emailMapper.toResponseDTO(adolescenteSalvo, dataModificacao);
    }

    @Transactional(readOnly = true)
    public Page<EmailListarResponseDTO> listar(Integer idAdolescente, Pageable pageable) {
        //Verifica se o adolescente existe e retorna ele
        Adolescente adolescente = adolescenteService.buscarAdolescente(idAdolescente);

        //Retorna todos que o adolescente tiver
        return emailRepository.findByAdolescenteIdAdolescenteOrderByDataInicioDesc(idAdolescente, pageable)
                .map(emailMapper::toListarResponseDTO);
    }

    @Transactional(readOnly = true)
    public EmailResponseDTO buscarPorId(Integer idAdolescente, Integer idEmail) {
        Email email = buscarEmailAdolescente(idAdolescente, idEmail);
        return emailMapper.toResponseDTO(email);
    }

    @Transactional
    public void excluirPorId(Integer idAdolescente, Integer idEmail) {
        Email email = buscarEmailAdolescente(idAdolescente, idEmail);
        emailRepository.deleteById(idEmail);
    }

    public void atualizarEmail(String dto, Adolescente adolescente, LocalDate data) {
        Email emailAtual = adolescente.getEmails().stream()
                .filter(e -> e.getTitular() == TitularContato.ADOLESCENTE)
                .filter(e -> e.getDataFim() == null)
                .max(Comparator.comparing(Email::getDataInicio))
                .orElse(null);

        if (dto == null || dto.isBlank()) {
            if (emailAtual != null) {
                emailAtual.setDataFim(data);
            }
            return;
        }

        if (emailAtual != null && emailAtual.getEmail().equals(dto)) {
            return;
        }

        if (emailAtual != null) {
            emailAtual.setDataFim(data);
        }

        criarEmail(dto, TitularContato.ADOLESCENTE, adolescente, data);
    }

    public void criarEmail(String emailNovo, TitularContato titular, Adolescente adolescente, LocalDate data) {
        Email email = new Email();
        email.setEmail(emailNovo);
        email.setTitular(titular);
        email.setDataInicio(data);

        adolescente.adicionarEmail(email);
    }

    public void atualizarEmail(String dto, Empresa empresa, LocalDate data) {
        Email emailAtual = empresa.getEmails().stream()
                .filter(e -> e.getTitular() == TitularContato.EMPRESA)
                .filter(e -> e.getDataFim() == null)
                .max(Comparator.comparing(Email::getDataInicio))
                .orElse(null);

        if (dto == null || dto.isBlank()) {
            if (emailAtual != null) {
                emailAtual.setDataFim(data);
            }
            return;
        }

        if (emailAtual != null && emailAtual.getEmail().equals(dto)) {
            return;
        }

        if (emailAtual != null) {
            emailAtual.setDataFim(data);
        }

        criarEmail(dto, TitularContato.EMPRESA, empresa, data);
    }

    public void criarEmail(String emailNovo, TitularContato titular, Empresa empresa, LocalDate data) {
        Email email = new Email();
        email.setEmail(emailNovo);
        email.setTitular(titular);
        email.setDataInicio(data);

        empresa.adicionarEmail(email);
    }

    private Email buscarEmailAdolescente(Integer idAdolescente, Integer idEmail) {
        Adolescente adolescente = adolescenteService.buscarAdolescente(idAdolescente);

        Email email = emailRepository.findById(idEmail)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Email com ID "+idEmail+" não encontrada."));

        if (email.getAdolescente() == null) {
            throw new RegraNegocioException("Email não pertence a um adolescente.");
        }

        if (!email.getAdolescente().getIdAdolescente().equals(idAdolescente)) {
            throw new RegraNegocioException("Email não pertence ao adolescente.");
        }

        return email;
    }

    private Empresa buscarEmpresa(Integer idEmpresa) {
        Empresa empresa = empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Empresa com ID "+idEmpresa+" não encontrada."));
        return empresa;
    }

    private Email buscarEmailEmpresa(Integer idEmpresa, Integer idEmail) {
        Empresa empresa = buscarEmpresa(idEmpresa);

        Email email = emailRepository.findById(idEmail)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Email com ID "+idEmail+" não encontrada."));

        if (email.getEmpresa() == null) {
            throw new RegraNegocioException("Email não pertence a uma empresa.");
        }

        if (!email.getEmpresa().getIdEmpresa().equals(idEmpresa)) {
            throw new RegraNegocioException("Email não pertence a empresa.");
        }

        return email;
    }


    @Transactional(readOnly = true)
    public Page<EmailListarResponseDTO> listarEmpresa(Integer idEmpresa, Pageable pageable) {
        //Verifica se a empresa existe e retorna ela
        Empresa empresa = buscarEmpresa(idEmpresa);

        //Retorna todos que a empresa tiver
        return emailRepository.findByEmpresaIdEmpresaOrderByDataInicioDesc(idEmpresa, pageable)
                .map(emailMapper::toListarResponseDTO);
    }

    @Transactional(readOnly = true)
    public EmailResponseDTO buscarEmpresaPorId(Integer idEmpresa, Integer idEmail) {
        Email email = buscarEmailEmpresa(idEmpresa, idEmail);
        return emailMapper.toResponseDTO(email);
    }

    @Transactional
    public void excluirEmpresaPorId(Integer idEmpresa, Integer idEmail) {
        Email email = buscarEmailEmpresa(idEmpresa, idEmail);
        emailRepository.deleteById(idEmail);
    }


}
