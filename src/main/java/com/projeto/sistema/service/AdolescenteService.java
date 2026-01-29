package com.projeto.sistema.service;

import com.projeto.sistema.dto.request.AdolescenteAtualizarRequestDTO;
import com.projeto.sistema.dto.request.AdolescenteInscricaoRequestDTO;
import com.projeto.sistema.dto.request.AdolescenteMatriculaRequestDTO;
import com.projeto.sistema.dto.response.AdolescenteListarResponseDTO;
import com.projeto.sistema.dto.response.AdolescenteMatriculaResponseDTO;
import com.projeto.sistema.dto.response.AdolescenteResponseDTO;
import com.projeto.sistema.dto.response.EmpresaCriarResponseDTO;
import com.projeto.sistema.enums.EstadoCivil;
import com.projeto.sistema.exceptions.RecursoNaoEncontradoException;
import com.projeto.sistema.mapper.AdolescenteMapper;
import com.projeto.sistema.model.Adolescente;
import com.projeto.sistema.model.Empresa;
import com.projeto.sistema.repository.AdolescenteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class AdolescenteService {
    private final AdolescenteRepository adolescenteRepository;
    private final AdolescenteMapper adolescenteMapper;

    public AdolescenteService(AdolescenteRepository adolescenteRepository, AdolescenteMapper adolescenteMapper) {
        this.adolescenteRepository = adolescenteRepository;
        this.adolescenteMapper = adolescenteMapper;
    }

    @Transactional(readOnly = true)
    public List<AdolescenteListarResponseDTO> listar() {
        return adolescenteRepository.findAll()
                .stream()
                .map(adolescenteMapper::toListarResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<AdolescenteListarResponseDTO> listar(Pageable pageable, String nome) {
        if (nome == null || nome.isBlank()) {
            return adolescenteRepository.findAll(pageable)
                    .map(adolescenteMapper::toListarResponseDTO);
        }
        return adolescenteRepository.findByNomeContainingIgnoreCase(nome, pageable)
                .map(adolescenteMapper::toListarResponseDTO);
    }

    /* //LISTAR MAIS EFICIENTE

    @Transactional(readOnly = true)
    public List<AdolescenteListarResponseDTO> listar() {

        // busca somente os dados básicos
        List<AdolescenteListarBaseDTO> base = adolescenteRepository.listarBaseParaSelect();

        // busca os adolescentes completos em lote (evita N+1)
        Map<Integer, Adolescente> adolescentes =
                adolescenteRepository.findAllById(
                        base.stream()
                                .map(AdolescenteListarBaseDTO::idAdolescente)
                                .toList()
                ).stream().collect(Collectors.toMap(
                        Adolescente::getIdAdolescente,
                        a -> a
                ));

        return base.stream()
                .map(dto -> {
                    Adolescente a = adolescentes.get(dto.idAdolescente());

                    return new AdolescenteListarResponseDTO(
                            dto.idAdolescente(),
                            dto.nome(),
                            dto.cpf(),
                            a.getSituacao()
                    );
                })
                .toList();
    }*/

    @Transactional(readOnly = true)
    public AdolescenteResponseDTO buscarPorId(Integer idAdolescente) {
        Adolescente adolescente = buscarAdolescente(idAdolescente);
        return adolescenteMapper.toResponseDTO(adolescente);
    }

    public AdolescenteMatriculaResponseDTO atualizar(Integer idAdolescente, AdolescenteAtualizarRequestDTO dto) {
        LocalDate dataModificacao = dto.dataModificacao();
        if(dataModificacao == null) {
            dataModificacao = LocalDate.now();
        }

        Adolescente adolescente = buscarAdolescente(idAdolescente);

        adolescente.setNome(dto.adolescente().nome());
        adolescente.setGenero(dto.adolescente().genero());
        adolescente.setDataNascimento(dto.adolescente().dataNascimento());
        if(dto.adolescente().cidadeNascimento() != null && !dto.adolescente().cidadeNascimento().isEmpty()) {
            adolescente.setCidadeNascimento(dto.adolescente().cidadeNascimento());
        }
        if(dto.adolescente().estadoNascimento() != null) {
            adolescente.setEstadoNascimento(dto.adolescente().estadoNascimento());
        }
        if(dto.adolescente().paisNascimento() != null && !dto.adolescente().paisNascimento().isEmpty()) {
            adolescente.setPaisNascimento(dto.adolescente().paisNascimento());
        }
        if(dto.adolescente().naturalidade() != null) {
            adolescente.setNaturalidade(dto.adolescente().naturalidade());
        }
        if(dto.adolescente().estadoCivil() != null) {
            adolescente.setEstadoCivil(dto.adolescente().estadoCivil());
        }
        if(dto.adolescente().mae() != null && !dto.adolescente().mae().isEmpty()) {
            adolescente.setMae(dto.adolescente().mae());
        }
        if(dto.adolescente().pai() != null && !dto.adolescente().pai().isEmpty()) {
            adolescente.setPai(dto.adolescente().pai());
        }
        if(dto.adolescente().estadoCivil() != null && dto.adolescente().estadoCivil() != EstadoCivil.SOLTEIRO) {
            adolescente.setConjuge(dto.adolescente().conjuge());
        }

        Adolescente adolescenteSalvo = salvar(adolescente);
        return adolescenteMapper.toResponseDTO(adolescenteSalvo, dataModificacao);
    }

    @Transactional
    public void excluirPorId(Integer idAdolescente) {
        Adolescente adolescente = buscarAdolescente(idAdolescente);
        adolescenteRepository.deleteById(idAdolescente);
    }

    public Adolescente criarAdolescenteInscricao(AdolescenteInscricaoRequestDTO dto) {
        Adolescente adolescente = new Adolescente();
        adolescente.setNome(dto.nome());
        adolescente.setGenero(dto.genero());
        adolescente.setDataNascimento(dto.dataNascimento());
        return adolescente;
    }

    public void atualizarAdolescenteInscricao(AdolescenteInscricaoRequestDTO dto, Adolescente adolescente) {
        adolescente.setNome(dto.nome());
        adolescente.setGenero(dto.genero());
        adolescente.setDataNascimento(dto.dataNascimento());
    }

    public Adolescente criarAdolescenteMatricula(AdolescenteMatriculaRequestDTO dto) {
        Adolescente adolescente = new Adolescente();
        adolescente.setNome(dto.nome());
        adolescente.setGenero(dto.genero());
        adolescente.setDataNascimento(dto.dataNascimento());
        adolescente.setCidadeNascimento(dto.cidadeNascimento());
        adolescente.setEstadoNascimento(dto.estadoNascimento());
        adolescente.setPaisNascimento(dto.paisNascimento());
        adolescente.setNaturalidade(dto.naturalidade());
        adolescente.setEstadoCivil(dto.estadoCivil());
        adolescente.setMae(dto.mae());
        adolescente.setPai(dto.pai());
        adolescente.setConjuge(dto.conjuge());
        return adolescente;
    }

    public void atualizarAdolescenteMatricula(AdolescenteMatriculaRequestDTO dto, Adolescente adolescente) {
        adolescente.setNome(dto.nome());
        adolescente.setGenero(dto.genero());
        adolescente.setDataNascimento(dto.dataNascimento());
        adolescente.setCidadeNascimento(dto.cidadeNascimento());
        adolescente.setEstadoNascimento(dto.estadoNascimento());
        adolescente.setPaisNascimento(dto.paisNascimento());
        adolescente.setNaturalidade(dto.naturalidade());
        adolescente.setEstadoCivil(dto.estadoCivil());
        adolescente.setMae(dto.mae());
        adolescente.setPai(dto.pai());
        adolescente.setConjuge(dto.conjuge());
    }

    @Transactional
    public Adolescente salvar(Adolescente adolescente) {
        Adolescente salvo = adolescenteRepository.save(adolescente);
        return salvo;
    }

    public Adolescente buscarAdolescente(Integer idAdolescente) {
        Adolescente adolescente = adolescenteRepository.findById(idAdolescente)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Adolescente com ID "+idAdolescente+" não encontrado."));
        return adolescente;
    }
}
