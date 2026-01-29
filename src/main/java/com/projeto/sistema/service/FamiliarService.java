package com.projeto.sistema.service;

import com.projeto.sistema.dto.request.FamiliarRequestDTO;
import com.projeto.sistema.dto.request.FamiliaresAtualizarRequestDTO;
import com.projeto.sistema.dto.request.FamiliaresRequestDTO;
import com.projeto.sistema.dto.request.ResponsavelRequestDTO;
import com.projeto.sistema.dto.response.FamiliarListarResponseDTO;
import com.projeto.sistema.dto.response.FamiliaresResponseDTO;
import com.projeto.sistema.dto.response.ResponsavelResponseDTO;
import com.projeto.sistema.exceptions.RecursoNaoEncontradoException;
import com.projeto.sistema.exceptions.RegraNegocioException;
import com.projeto.sistema.mapper.FamiliarMapper;
import com.projeto.sistema.model.Adolescente;
import com.projeto.sistema.model.Escolaridade;
import com.projeto.sistema.model.Familiar;
import com.projeto.sistema.repository.FamiliarRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class FamiliarService {
    private final FamiliarRepository familiarRepository;
    private final DocumentoService documentoService;
    private final AdolescenteService adolescenteService;
    private final FamiliarMapper familiarMapper;

    public FamiliarService(FamiliarRepository familiarRepository, DocumentoService documentoService, AdolescenteService adolescenteService, FamiliarMapper familiarMapper) {
        this.familiarRepository = familiarRepository;
        this.documentoService = documentoService;
        this.adolescenteService = adolescenteService;
        this.familiarMapper = familiarMapper;
    }

    @Transactional
    public FamiliaresResponseDTO atualizar(Integer idAdolescente, FamiliaresAtualizarRequestDTO dto) {
        LocalDate dataModificacao = dto.dataModificacao();
        if(dataModificacao == null) {
            dataModificacao = LocalDate.now();
        }

        //Verifica se o adolescente existe e retorna ele
        Adolescente adolescente = adolescenteService.buscarAdolescente(idAdolescente);

        //Atualiza
        atualizarFamiliares(dto.familiares(), adolescente, dataModificacao);

        //Salva o adolescente no banco
        Adolescente adolescenteSalvo = adolescenteService.salvar(adolescente);

        //Retorna o ResponseDTO
        return familiarMapper.toResponseDTO(adolescenteSalvo, dataModificacao);
    }

    @Transactional(readOnly = true)
    public Page<FamiliarListarResponseDTO> listar(Integer idAdolescente, Pageable pageable) {
        //Verifica se o adolescente existe e retorna ele
        Adolescente adolescente = adolescenteService.buscarAdolescente(idAdolescente);

        //Retorna todos que o adolescente tiver
        return familiarRepository.findByAdolescenteIdAdolescenteOrderByDataInicioDesc(idAdolescente, pageable)
                .map(familiarMapper::toListarResponseDTO);
    }

    @Transactional(readOnly = true)
    public ResponsavelResponseDTO buscarPorId(Integer idAdolescente, Integer idFamiliar) {
        Familiar familiar = buscarFamiliarAdolescente(idAdolescente, idFamiliar);
        return familiarMapper.toResponseDTO(familiar);
    }

    @Transactional
    public void excluirPorId(Integer idAdolescente, Integer idFamiliar) {
        Familiar familiar = buscarFamiliarAdolescente(idAdolescente, idFamiliar);
        familiarRepository.deleteById(idFamiliar);
    }


    public void atualizarFamiliares(FamiliaresRequestDTO dto, Adolescente adolescente, LocalDate data) {
        if (dto.responsavel() == null) {
            encerrarResponsavel(adolescente, data);
        } else {
            atualizarResponsavel(dto.responsavel(), adolescente, data);
        }

        if (dto.familiares() == null || dto.familiares().isEmpty()) {
            encerrarFamiliaresSemDocumento(adolescente, data);
        } else {
            atualizarFamiliaresComuns(dto.familiares(), adolescente, data);
        }
    }

    private void atualizarResponsavel(ResponsavelRequestDTO dto, Adolescente adolescente, LocalDate data) {

        Familiar responsavelAtual = buscarResponsavelAtivo(adolescente);

        if (responsavelAtual != null && responsavelIgual(dto, responsavelAtual)) {
            return; // não mudou
        }

        if (responsavelAtual != null) {
            responsavelAtual.setDataFim(data);
        }

        criarResponsavel(dto, adolescente, data);
    }

    private void encerrarResponsavel(Adolescente adolescente, LocalDate data) {
        adolescente.getFamiliares().stream()
                .filter(this::isResponsavel)
                .filter(f -> f.getDataFim() == null)
                .forEach(f -> f.setDataFim(data));
    }

    private void atualizarFamiliaresComuns(List<FamiliarRequestDTO> dtos, Adolescente adolescente, LocalDate data) {

        // Atualiza ou cria
        for (FamiliarRequestDTO dto : dtos) {

            Familiar familiarAtual = buscarFamiliarAtivoPorNome(adolescente, dto.nome());

            if (familiarAtual != null && familiarIgual(dto, familiarAtual)) {
                continue;
            }

            if (familiarAtual != null) {
                familiarAtual.setDataFim(data);
            }

            criarFamiliar(dto, adolescente, data);
        }

        // Encerra os que não vieram no DTO
        adolescente.getFamiliares().stream()
                .filter(f -> !isResponsavel(f))
                .filter(f -> f.getDataFim() == null)
                .filter(f -> dtos.stream()
                        .noneMatch(dto ->
                                normalizar(dto.nome())
                                        .equals(normalizar(f.getNome()))
                        ))
                .forEach(f -> f.setDataFim(data));
    }

    private void encerrarFamiliaresSemDocumento(Adolescente adolescente, LocalDate data) {
        adolescente.getFamiliares().stream()
                .filter(f -> !isResponsavel(f))
                .filter(f -> f.getDataFim() == null)
                .forEach(f -> f.setDataFim(data));
    }

    public void criarFamiliares(FamiliaresRequestDTO dto, Adolescente adolescente, LocalDate data) {

        if (dto.responsavel() != null) {
            criarResponsavel(dto.responsavel(), adolescente, data);
        }

        if (dto.familiares() != null) {
            dto.familiares().forEach(f ->
                    criarFamiliar(f, adolescente, data)
            );
        }
    }

    public void criarFamiliar(FamiliarRequestDTO dto, Adolescente adolescente, LocalDate data) {
        Familiar familiar = new Familiar();
        familiar.setNome(dto.nome());
        familiar.setParentesco(dto.parentesco());
        familiar.setIdade(dto.idade());
        familiar.setEscolaridade(dto.escolaridade());
        familiar.setProfissao(dto.profissao());
        familiar.setLocalTrabalho(dto.localTrabalho());
        familiar.setRenda(dto.renda());
        familiar.setDataInicio(data);

        adolescente.adicionarFamiliar(familiar);
    }

    public void criarResponsavel(ResponsavelRequestDTO dto, Adolescente adolescente, LocalDate data) {
        Familiar responsavel = new Familiar();
        responsavel.setNome(dto.nome());
        responsavel.setParentesco(dto.parentesco());
        responsavel.setIdade(dto.idade());
        responsavel.setEscolaridade(dto.escolaridade());
        responsavel.setProfissao(dto.profissao());
        responsavel.setLocalTrabalho(dto.localTrabalho());
        responsavel.setRenda(dto.renda());
        responsavel.setReside(dto.reside());
        responsavel.setNaturalidade(dto.naturalidade());
        responsavel.setEstadoCivil(dto.estadoCivil());
        responsavel.setDataInicio(data);

        adolescente.adicionarFamiliar(responsavel);

        documentoService.criarOuAtualizarDocumentoResponsavel(dto.documento(), responsavel);
    }


    public boolean familiarIgual(FamiliarRequestDTO dto, Familiar f) {
        return Objects.equals(f.getNome(), dto.nome()) &&
                Objects.equals(f.getIdade(), dto.idade()) &&
                Objects.equals(f.getParentesco(), dto.parentesco()) &&
                Objects.equals(f.getEscolaridade(), dto.escolaridade()) &&
                Objects.equals(f.getProfissao(), dto.profissao()) &&
                Objects.equals(f.getLocalTrabalho(), dto.localTrabalho()) &&
                Objects.equals(f.getRenda(), dto.renda());
    }

    public boolean responsavelIgual(ResponsavelRequestDTO dto, Familiar f) {
        return Objects.equals(f.getNome(), dto.nome()) &&
                Objects.equals(f.getIdade(), dto.idade()) &&
                Objects.equals(f.getParentesco(), dto.parentesco()) &&
                Objects.equals(f.getEscolaridade(), dto.escolaridade()) &&
                Objects.equals(f.getProfissao(), dto.profissao()) &&
                Objects.equals(f.getLocalTrabalho(), dto.localTrabalho()) &&
                Objects.equals(f.getRenda(), dto.renda()) &&
                Objects.equals(f.getReside(), dto.reside()) &&
                Objects.equals(f.getNaturalidade(), dto.naturalidade()) &&
                Objects.equals(f.getEstadoCivil(), dto.estadoCivil());
    }

    private Familiar buscarFamiliarAtivoPorNome(Adolescente adolescente, String nome) {
        return adolescente.getFamiliares().stream()
                .filter(f -> !isResponsavel(f))
                .filter(f -> f.getDataFim() == null)
                .filter(f ->
                        normalizar(f.getNome())
                                .equals(normalizar(nome))
                )
                .max(Comparator.comparing(Familiar::getDataInicio))
                .orElse(null);
    }

    private Familiar buscarResponsavelAtivo(Adolescente adolescente) {
        return adolescente.getFamiliares().stream()
                .filter(this::isResponsavel)
                .filter(f -> f.getDataFim() == null)
                .max(Comparator.comparing(Familiar::getDataInicio))
                .orElse(null);
    }

    private boolean isResponsavel(Familiar f) {
        return f.getDocumento() != null;
    }

    private String normalizar(String valor) {
        return valor == null
                ? null
                : valor.trim().toUpperCase();
    }

    private Familiar buscarFamiliarAdolescente(Integer idAdolescente, Integer idFamiliar) {
        Adolescente adolescente = adolescenteService.buscarAdolescente(idAdolescente);

        Familiar familiar = familiarRepository.findById(idFamiliar)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Familiar com ID "+idFamiliar+" não encontrada."));

        if (familiar.getAdolescente() == null) {
            throw new RegraNegocioException("Familiar não pertence a um adolescente.");
        }

        if (!familiar.getAdolescente().getIdAdolescente().equals(idAdolescente)) {
            throw new RegraNegocioException("Familiar não pertence ao adolescente.");
        }

        return familiar;
    }


}
