package com.projeto.cefom.novos.mapper;

import com.projeto.cefom.novos.dto.response.AlunoParticipacaoResponseDTO;
import com.projeto.cefom.novos.dto.response.TurmaListarResponseDTO;
import com.projeto.cefom.novos.dto.response.TurmaResponseDTO;
import com.projeto.cefom.novos.dto.response.TurmaSelectResponseDTO;
import com.projeto.cefom.novos.model.Turma;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class TurmaMapper {

    private final ParticipacaoMapper participacaoMapper;

    public TurmaMapper(ParticipacaoMapper participacaoMapper) {
        this.participacaoMapper = participacaoMapper;
    }

    public TurmaResponseDTO toResponseDTO(Turma turma, LocalDate data) {
        if (turma == null) return null;

        List<AlunoParticipacaoResponseDTO> alunos = turma.getAlunos()
                .stream()
                .filter(p -> p.participaEm(data))
                .map(p -> participacaoMapper.toResponseDTO(p, data))
                .toList();

        return new TurmaResponseDTO(
                turma.getIdTurma(),
                turma.getNome(),
                turma.getTipo(),
                alunos
        );
    }

    public TurmaListarResponseDTO toListarResponseDTO(Turma turma) {
        if (turma == null) return null;
        return new TurmaListarResponseDTO(
                turma.getIdTurma(),
                turma.getNome(),
                turma.getTipo()
        );
    }

    public TurmaSelectResponseDTO toSelectResponseDTO(Turma turma) {
        if (turma == null) return null;
        return new TurmaSelectResponseDTO(
                turma.getIdTurma(),
                turma.getNome()
        );
    }
}
