package com.projeto.cefom.service;

import com.projeto.cefom.exceptions.RecursoNaoEncontradoException;
import com.projeto.cefom.exceptions.RegraNegocioException;
import com.projeto.cefom.dto.request.ProfessorRequestDTO;
import com.projeto.cefom.dto.response.ProfessorResponseDTO;
import com.projeto.cefom.mapper.ProfessorMapper;
import com.projeto.cefom.model.Professor;
import com.projeto.cefom.repository.ProfessorRepository;
import com.projeto.cefom.utils.TextoUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProfessorService {
    private final ProfessorRepository professorRepository;
    private final ProfessorMapper professorMapper;

    public ProfessorService(ProfessorRepository professorRepository, ProfessorMapper professorMapper) {
        this.professorRepository = professorRepository;
        this.professorMapper = professorMapper;
    }

    @Transactional
    public ProfessorResponseDTO criar(ProfessorRequestDTO dto) {
        if (professorRepository.existsByNome(TextoUtils.normalizar(dto.nome()))) {
            throw new RegraNegocioException("Professor já cadastrado.");
        }
        Professor professor = salvar(criarProfessor(dto));
        return professorMapper.toResponseDTO(professor);
    }

    @Transactional
    public ProfessorResponseDTO atualizar(Integer idProfessor, ProfessorRequestDTO dto) {
        if (professorRepository.existsByNomeAndIdProfessorNot(TextoUtils.normalizar(dto.nome()), idProfessor)) {
            throw new RegraNegocioException("Professor já cadastrado.");
        }
        Professor professor = buscarProfessor(idProfessor);
        professor = salvar(atualizarProfessor(dto, professor));
        return professorMapper.toResponseDTO(professor);
    }

    @Transactional(readOnly = true)
    public ProfessorResponseDTO buscarPorId(Integer idProfessor) {
        Professor professor = buscarProfessor(idProfessor);
        return professorMapper.toResponseDTO(professor);
    }

    @Transactional(readOnly = true)
    public Page<ProfessorResponseDTO> listar(Pageable pageable, String nome) {
        if (nome == null || nome.isBlank()) {
            return professorRepository.findAll(pageable)
                    .map(professorMapper::toResponseDTO);
        }
        return professorRepository.findByNomeContainingIgnoreCase(TextoUtils.normalizar(nome), pageable)
                .map(professorMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public List<ProfessorResponseDTO> listarSelect() {
        return professorRepository.findAll()
                .stream()
                .map(professorMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public void excluirPorId(Integer idProfessor) {
        Professor professor = buscarProfessor(idProfessor);
        professorRepository.deleteById(idProfessor);
    }

    public Professor salvar(Professor professor) {
        return professorRepository.save(professor);
    }

    public Professor buscarProfessor(Integer idProfessor) {
        Professor professor = professorRepository.findById(idProfessor)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Professor com ID "+idProfessor+" não encontrado."));
        return professor;
    }

    public Professor criarProfessor(ProfessorRequestDTO dto) {
        Professor professor = new Professor();
        professor.setNome(dto.nome());
        return professor;
    }

    public Professor atualizarProfessor(ProfessorRequestDTO dto, Professor professor) {
        professor.setNome(dto.nome());
        return professor;
    }
}
