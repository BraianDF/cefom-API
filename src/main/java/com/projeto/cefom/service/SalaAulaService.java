package com.projeto.cefom.service;

import com.projeto.cefom.exceptions.RecursoNaoEncontradoException;
import com.projeto.cefom.exceptions.RegraNegocioException;
import com.projeto.cefom.dto.request.SalaAulaRequestDTO;
import com.projeto.cefom.dto.response.SalaAulaResponseDTO;
import com.projeto.cefom.mapper.SalaAulaMapper;
import com.projeto.cefom.model.SalaAula;
import com.projeto.cefom.repository.SalaAulaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SalaAulaService {
    private final SalaAulaRepository salaAulaRepository;
    private final SalaAulaMapper salaAulaMapper;

    public SalaAulaService(SalaAulaRepository salaAulaRepository, SalaAulaMapper salaAulaMapper) {
        this.salaAulaRepository = salaAulaRepository;
        this.salaAulaMapper = salaAulaMapper;
    }

    @Transactional
    public SalaAulaResponseDTO criar(SalaAulaRequestDTO dto) {
        if (salaAulaRepository.existsByNome(dto.nome())) {
            throw new RegraNegocioException("Sala de Aula já cadastrada.");
        }
        SalaAula salaAula = salvar(criarSalaAula(dto));
        return salaAulaMapper.toResponseDTO(salaAula);
    }

    @Transactional
    public SalaAulaResponseDTO atualizar(Integer idSalaAula, SalaAulaRequestDTO dto) {
        if (salaAulaRepository.existsByNomeAndIdSalaAulaNot(dto.nome(), idSalaAula)) {
            throw new RegraNegocioException("Sala de Aula já cadastrada.");
        }
        SalaAula salaAula = buscarSalaAula(idSalaAula);
        salaAula = salvar(atualizarSalaAula(dto, salaAula));
        return salaAulaMapper.toResponseDTO(salaAula);
    }

    @Transactional(readOnly = true)
    public SalaAulaResponseDTO buscarPorId(Integer idSalaAula) {
        SalaAula salaAula = buscarSalaAula(idSalaAula);
        return salaAulaMapper.toResponseDTO(salaAula);
    }

    @Transactional(readOnly = true)
    public Page<SalaAulaResponseDTO> listar(Pageable pageable, String nome) {
        if (nome == null || nome.isBlank()) {
            return salaAulaRepository.findAll(pageable)
                    .map(salaAulaMapper::toResponseDTO);
        }
        return salaAulaRepository.findByNomeContainingIgnoreCase(nome, pageable)
                .map(salaAulaMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public List<SalaAulaResponseDTO> listarSelect() {
        return salaAulaRepository.findAll()
                .stream()
                .map(salaAulaMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public void excluirPorId(Integer idSalaAula) {
        SalaAula salaAula = buscarSalaAula(idSalaAula);
        salaAulaRepository.deleteById(idSalaAula);
    }

    public SalaAula salvar(SalaAula salaAula) {
        return salaAulaRepository.save(salaAula);
    }

    public SalaAula buscarSalaAula(Integer idSalaAula) {
        SalaAula salaAula = salaAulaRepository.findById(idSalaAula)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Sala de Aula com ID "+idSalaAula+" não encontrada."));
        return salaAula;
    }

    public SalaAula criarSalaAula(SalaAulaRequestDTO dto) {
        SalaAula salaAula = new SalaAula();
        salaAula.setNome(dto.nome());
        return salaAula;
    }

    public SalaAula atualizarSalaAula(SalaAulaRequestDTO dto, SalaAula salaAula) {
        salaAula.setNome(dto.nome());
        return salaAula;
    }
}
