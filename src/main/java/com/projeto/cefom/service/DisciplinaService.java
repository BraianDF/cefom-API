package com.projeto.cefom.service;

import com.projeto.cefom.exceptions.RecursoNaoEncontradoException;
import com.projeto.cefom.exceptions.RegraNegocioException;
import com.projeto.cefom.dto.request.DisciplinaRequestDTO;
import com.projeto.cefom.dto.response.DisciplinaResponseDTO;
import com.projeto.cefom.mapper.DisciplinaMapper;
import com.projeto.cefom.model.Disciplina;
import com.projeto.cefom.repository.DisciplinaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class DisciplinaService {
    private final DisciplinaRepository disciplinaRepository;
    private final DisciplinaMapper disciplinaMapper;

    public DisciplinaService(DisciplinaRepository disciplinaRepository, DisciplinaMapper disciplinaMapper) {
        this.disciplinaRepository = disciplinaRepository;
        this.disciplinaMapper = disciplinaMapper;
    }

    @Transactional
    public DisciplinaResponseDTO criar(DisciplinaRequestDTO dto) {
        if (disciplinaRepository.existsByNome(dto.nome())) {
            throw new RegraNegocioException("Disciplina já cadastrada.");
        }
        Disciplina disciplina = salvar(criarDisciplina(dto));
        return disciplinaMapper.toResponseDTO(disciplina);
    }

    @Transactional
    public DisciplinaResponseDTO atualizar(Integer idDisciplina, DisciplinaRequestDTO dto) {
        if (disciplinaRepository.existsByNomeAndIdDisciplinaNot(dto.nome(), idDisciplina)) {
            throw new RegraNegocioException("Disciplina já cadastrada.");
        }
        Disciplina disciplina = buscarDisciplina(idDisciplina);
        disciplina = salvar(atualizarDisciplina(dto, disciplina));
        return disciplinaMapper.toResponseDTO(disciplina);
    }

    @Transactional(readOnly = true)
    public DisciplinaResponseDTO buscarPorId(Integer idDisciplina) {
        Disciplina disciplina = buscarDisciplina(idDisciplina);
        return disciplinaMapper.toResponseDTO(disciplina);
    }

    @Transactional(readOnly = true)
    public Page<DisciplinaResponseDTO> listar(Pageable pageable, String nome) {
        if (nome == null || nome.isBlank()) {
            return disciplinaRepository.findAll(pageable)
                    .map(disciplinaMapper::toResponseDTO);
        }
        return disciplinaRepository.findByNomeContainingIgnoreCase(nome, pageable)
                .map(disciplinaMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public List<DisciplinaResponseDTO> listarSelect() {
        return disciplinaRepository.findAll()
                .stream()
                .map(disciplinaMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public void excluirPorId(Integer idDisciplina) {
        Disciplina disciplina = buscarDisciplina(idDisciplina);
        disciplinaRepository.deleteById(idDisciplina);
    }

    public Disciplina salvar(Disciplina disciplina) {
        return disciplinaRepository.save(disciplina);
    }

    public Disciplina buscarDisciplina(Integer idDisciplina) {
        Disciplina disciplina = disciplinaRepository.findById(idDisciplina)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Disciplina com ID "+idDisciplina+" não encontrada."));
        return disciplina;
    }

    public Disciplina criarDisciplina(DisciplinaRequestDTO dto) {
        Disciplina disciplina = new Disciplina();
        disciplina.setNome(dto.nome());
        return disciplina;
    }

    public Disciplina atualizarDisciplina(DisciplinaRequestDTO dto, Disciplina disciplina) {
        disciplina.setNome(dto.nome());
        return disciplina;
    }
}
