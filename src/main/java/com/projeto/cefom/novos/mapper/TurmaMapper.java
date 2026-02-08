package com.projeto.cefom.novos.mapper;

import com.projeto.cefom.novos.dto.response.TurmaListarResponseDTO;
import com.projeto.cefom.novos.dto.response.TurmaResponseDTO;
import com.projeto.cefom.novos.dto.response.TurmaSelectResponseDTO;
import com.projeto.cefom.novos.model.Turma;
import org.springframework.stereotype.Component;

@Component
public class TurmaMapper {

    public TurmaResponseDTO toResponseDTO(Turma turma) {
        if (turma == null) return null;
        return new TurmaResponseDTO(
                turma.getIdTurma(),
                turma.getNome(),
                turma.getTipo()
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
