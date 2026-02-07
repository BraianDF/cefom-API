package com.projeto.cefom.novos.service;

import com.projeto.cefom.exceptions.RecursoNaoEncontradoException;
import com.projeto.cefom.exceptions.RegraNegocioException;
import com.projeto.cefom.novos.dto.request.TurmaRequestDTO;
import com.projeto.cefom.novos.dto.response.TurmaResponseDTO;
import com.projeto.cefom.novos.mapper.TurmaMapper;
import com.projeto.cefom.novos.model.Turma;
import com.projeto.cefom.novos.repository.TurmaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TurmaService {

    private final TurmaRepository turmaRepository;
    private final TurmaMapper turmaMapper;

    public TurmaService(TurmaRepository turmaRepository, TurmaMapper turmaMapper) {
        this.turmaRepository = turmaRepository;
        this.turmaMapper = turmaMapper;
    }

    @Transactional
    public TurmaResponseDTO criar(TurmaRequestDTO dto) {
        if (turmaRepository.existsByNome(dto.nome())) {
            throw new RegraNegocioException("Turma já cadastrada.");
        }
        Turma turma = salvar(criarTurma(dto));
        return turmaMapper.toResponseDTO(turma);
    }

    @Transactional
    public TurmaResponseDTO salvar(Integer idTurma, TurmaRequestDTO dto) {
        if (turmaRepository.existsByNomeAndIdTurmaNot(dto.nome(), idTurma)) {
            throw new RegraNegocioException("Turma já cadastrada.");
        }
        Turma turma = buscarTurma(idTurma);
        turma = salvar(atualizarTurma(dto, turma));
        return turmaMapper.toResponseDTO(turma);
    }

    public Turma salvar(Turma turma) {
        return turmaRepository.save(turma);
    }

    public Turma buscarTurma(Integer idTurma) {
        Turma turma = turmaRepository.findById(idTurma)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Turma com ID "+idTurma+" não encontrada."));
        return turma;
    }

    public Turma criarTurma(TurmaRequestDTO dto) {
        Turma turma = new Turma();
        turma.setNome(dto.nome());
        turma.setTipo(dto.tipo());
        return turma;
    }

    public Turma atualizarTurma(TurmaRequestDTO dto, Turma turma) {
        turma.setNome(dto.nome());
        turma.setTipo(dto.tipo());
        return turma;
    }
}
