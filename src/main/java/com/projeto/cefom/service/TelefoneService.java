package com.projeto.cefom.service;

import com.projeto.cefom.dto.request.TelefonesAtualizarRequestDTO;
import com.projeto.cefom.dto.request.TelefonesRequestDTO;
import com.projeto.cefom.dto.response.TelefoneListarResponseDTO;
import com.projeto.cefom.dto.response.TelefoneResponseDTO;
import com.projeto.cefom.dto.response.TelefonesResponseDTO;
import com.projeto.cefom.enums.TitularContato;
import com.projeto.cefom.exceptions.RecursoNaoEncontradoException;
import com.projeto.cefom.exceptions.RegraNegocioException;
import com.projeto.cefom.mapper.TelefoneMapper;
import com.projeto.cefom.model.Adolescente;
import com.projeto.cefom.model.Telefone;
import com.projeto.cefom.dto.request.TelefonesEmpresaRequestDTO;
import com.projeto.cefom.model.Empresa;
import com.projeto.cefom.repository.EmpresaRepository;
import com.projeto.cefom.repository.TelefoneRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;

@Service
public class TelefoneService {

    private final TelefoneRepository telefoneRepository;
    private final AdolescenteService adolescenteService;
    private final TelefoneMapper telefoneMapper;

    private final EmpresaRepository empresaRepository;

    public TelefoneService(TelefoneRepository telefoneRepository, AdolescenteService adolescenteService, TelefoneMapper telefoneMapper, EmpresaRepository empresaRepository) {
        this.telefoneRepository = telefoneRepository;
        this.adolescenteService = adolescenteService;
        this.telefoneMapper = telefoneMapper;
        this.empresaRepository = empresaRepository;
    }

    @Transactional
    public TelefonesResponseDTO atualizar(Integer idAdolescente, TelefonesAtualizarRequestDTO dto) {
        LocalDate dataModificacao = dto.dataModificacao();
        if(dataModificacao == null) {
            dataModificacao = LocalDate.now();
        }

        //Verifica se o adolescente existe e retorna ele
        Adolescente adolescente = adolescenteService.buscarAdolescente(idAdolescente);

        //Atualiza
        atualizarTelefones(dto.telefones(), adolescente, dataModificacao);

        //Salva o adolescente no banco
        Adolescente adolescenteSalvo = adolescenteService.salvar(adolescente);

        //Retorna o ResponseDTO
        return telefoneMapper.toResponseDTO(adolescenteSalvo, dataModificacao);
    }

    @Transactional(readOnly = true)
    public Page<TelefoneListarResponseDTO> listar(Integer idAdolescente, Pageable pageable) {
        //Verifica se o adolescente existe e retorna ele
        Adolescente adolescente = adolescenteService.buscarAdolescente(idAdolescente);

        //Retorna todos que o adolescente tiver
        return telefoneRepository.findByAdolescenteIdAdolescenteOrderByDataInicioDesc(idAdolescente, pageable)
                .map(telefoneMapper::toListarResponseDTO);
    }

    @Transactional(readOnly = true)
    public TelefoneResponseDTO buscarPorId(Integer idAdolescente, Integer idTelefone) {
        Telefone telefone = buscarTelefoneAdolescente(idAdolescente, idTelefone);
        return telefoneMapper.toResponseDTO(telefone);
    }

    @Transactional
    public void excluirPorId(Integer idAdolescente, Integer idTelefone) {
        Telefone telefone = buscarTelefoneAdolescente(idAdolescente, idTelefone);
        telefoneRepository.deleteById(idTelefone);
    }

    public void atualizarTelefones(TelefonesRequestDTO dto, Adolescente adolescente, LocalDate data) {

        atualizarTelefonePorTitular(adolescente, TitularContato.ADOLESCENTE,dto.telefoneAdolescente(), data);
        atualizarTelefonePorTitular(adolescente, TitularContato.RESPONSAVEL,dto.telefoneResponsavel(), data);
        atualizarTelefonePorTitular(adolescente, TitularContato.EXTRA,dto.telefoneExtra(), data);
    }

    public void atualizarTelefonePorTitular(Adolescente adolescente, TitularContato titular, String telefoneDto, LocalDate data) {

        Telefone telefoneAtual = adolescente.getTelefones().stream()
                .filter(t -> t.getTitular() == titular)
                .filter(t -> t.getDataFim() == null)
                .max(Comparator.comparing(Telefone::getDataInicio))
                .orElse(null);

        // Se não veio telefone no DTO
        if (telefoneDto == null || telefoneDto.isBlank()) {
            if (telefoneAtual != null) {
                telefoneAtual.setDataFim(data);
            }
            return;
        }

        String numeroLimpo = limparTelefone(telefoneDto);

        // Se não mudou, não faz nada
        if (telefoneAtual != null &&
                telefoneAtual.getNumero().equals(numeroLimpo)) {
            return;
        }

        // Encerra telefone atual
        if (telefoneAtual != null) {
            telefoneAtual.setDataFim(data);
        }

        // Cria novo telefone
        criarTelefone(numeroLimpo, titular, adolescente, data);
    }

    public void criarTelefones(TelefonesRequestDTO dto, Adolescente adolescente, LocalDate data) {
        if (dto.telefoneAdolescente() != null && !dto.telefoneAdolescente().isBlank()) {
            criarTelefone(dto.telefoneAdolescente(), TitularContato.ADOLESCENTE, adolescente, data);
        }
        if (dto.telefoneResponsavel() != null && !dto.telefoneResponsavel().isBlank()) {
            criarTelefone(dto.telefoneResponsavel(), TitularContato.RESPONSAVEL, adolescente, data);
        }
        if (dto.telefoneExtra() != null && !dto.telefoneExtra().isBlank()) {
            criarTelefone(dto.telefoneExtra(), TitularContato.EXTRA, adolescente, data);
        }
    }

    public void criarTelefone(String numero, TitularContato titular, Adolescente adolescente, LocalDate data) {
        Telefone telefone = new Telefone();
        telefone.setNumero(limparTelefone(numero));
        telefone.setTitular(titular);
        telefone.setDataInicio(data);

        adolescente.adicionarTelefone(telefone);
    }

    public void criarTelefones(TelefonesEmpresaRequestDTO dto, Empresa empresa, LocalDate data) {
        if (dto.telefonePrincipal() != null && !dto.telefonePrincipal().isBlank()) {
            criarTelefone(dto.telefonePrincipal(), TitularContato.EMPRESA, empresa, data);
        }

        if (dto.telefoneExtra() != null && !dto.telefoneExtra().isBlank()) {
            criarTelefone(dto.telefoneExtra(), TitularContato.EXTRA, empresa, data);
        }
    }

    public void criarTelefone(String numero, TitularContato titular, Empresa empresa, LocalDate data) {
        Telefone telefone = new Telefone();
        telefone.setNumero(limparTelefone(numero));
        telefone.setTitular(titular);
        telefone.setDataInicio(data);

        empresa.adicionarTelefone(telefone);
    }

    public void atualizarTelefones(TelefonesEmpresaRequestDTO dto, Empresa empresa, LocalDate data) {
        atualizarTelefonePorTitular(empresa, TitularContato.EMPRESA, dto.telefonePrincipal(), data);
        atualizarTelefonePorTitular(empresa, TitularContato.EXTRA, dto.telefoneExtra(), data);
    }

    public void atualizarTelefonePorTitular(Empresa empresa, TitularContato titular, String telefoneDto, LocalDate data) {

        Telefone telefoneAtual = empresa.getTelefones().stream()
                .filter(t -> t.getTitular() == titular)
                .filter(t -> t.getDataFim() == null)
                .max(Comparator.comparing(Telefone::getDataInicio))
                .orElse(null);

        // Se não veio telefone no DTO
        if (telefoneDto == null || telefoneDto.isBlank()) {
            if (telefoneAtual != null) {
                telefoneAtual.setDataFim(data);
            }
            return;
        }

        String numeroLimpo = limparTelefone(telefoneDto);

        // Se não mudou, não faz nada
        if (telefoneAtual != null &&
                telefoneAtual.getNumero().equals(numeroLimpo)) {
            return;
        }

        // Encerra telefone atual
        if (telefoneAtual != null) {
            telefoneAtual.setDataFim(data);
        }

        // Cria novo telefone
        criarTelefone(numeroLimpo, titular, empresa, data);
    }

    public Telefone buscarTelefoneAtivo(
            Adolescente adolescente,
            TitularContato titular,
            LocalDate data
    ) {
        return adolescente.getTelefones()
                .stream()
                .filter(t -> t.getTitular() == titular)
                .filter(t -> t.estaValidoEm(data))
                .max(Comparator.comparing(Telefone::getDataInicio))
                .orElse(null);
    }

    public String limparTelefone(String numero) {
        return numero.replaceAll("\\D", "");
    }

    private Telefone buscarTelefoneAdolescente(Integer idAdolescente, Integer idTelefone) {
        Adolescente adolescente = adolescenteService.buscarAdolescente(idAdolescente);

        Telefone telefone = telefoneRepository.findById(idTelefone)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Telefone com ID "+idTelefone+" não encontrado."));

        if (telefone.getAdolescente() == null) {
            throw new RegraNegocioException("Telefone não pertence a um adolescente.");
        }

        if (!telefone.getAdolescente().getIdAdolescente().equals(idAdolescente)) {
            throw new RegraNegocioException("Telefone não pertence ao adolescente.");
        }

        return telefone;
    }

    private Empresa buscarEmpresa(Integer idEmpresa) {
        Empresa empresa = empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Empresa com ID "+idEmpresa+" não encontrada."));
        return empresa;
    }

    private Telefone buscarTelefoneEmpresa(Integer idEmpresa, Integer idTelefone) {
        Empresa empresa = buscarEmpresa(idEmpresa);

        Telefone telefone = telefoneRepository.findById(idTelefone)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Telefone com ID "+idTelefone+" não encontrado."));

        if (telefone.getEmpresa() == null) {
            throw new RegraNegocioException("Telefone não pertence a uma empresa.");
        }

        if (!telefone.getEmpresa().getIdEmpresa().equals(idEmpresa)) {
            throw new RegraNegocioException("Telefone não pertence a empresa.");
        }

        return telefone;
    }

    @Transactional(readOnly = true)
    public Page<TelefoneListarResponseDTO> listarEmpresa(Integer idEmpresa, Pageable pageable) {
        //Verifica se o adolescente existe e retorna ele
        Empresa empresa = buscarEmpresa(idEmpresa);

        //Retorna todos que o adolescente tiver
        return telefoneRepository.findByEmpresaIdEmpresaOrderByDataInicioDesc(idEmpresa, pageable)
                .map(telefoneMapper::toListarResponseDTO);
    }

    @Transactional(readOnly = true)
    public TelefoneResponseDTO buscarEmpresaPorId(Integer idEmpresa, Integer idTelefone) {
        Telefone telefone = buscarTelefoneEmpresa(idEmpresa, idTelefone);
        return telefoneMapper.toResponseDTO(telefone);
    }

    @Transactional
    public void excluirEmpresaPorId(Integer idEmpresa, Integer idTelefone) {
        Telefone telefone = buscarTelefoneEmpresa(idEmpresa, idTelefone);
        telefoneRepository.deleteById(idTelefone);
    }
}
