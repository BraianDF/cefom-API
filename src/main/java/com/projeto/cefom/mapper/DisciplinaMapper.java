package com.projeto.cefom.mapper;

import com.projeto.cefom.dto.response.DisciplinaResponseDTO;
import com.projeto.cefom.model.Disciplina;
import com.projeto.cefom.utils.TextoUtils;
import org.springframework.stereotype.Component;

@Component
public class DisciplinaMapper {
    public DisciplinaResponseDTO toResponseDTO(Disciplina disciplina) {
        if (disciplina == null) return null;
        return new DisciplinaResponseDTO(
                disciplina.getIdDisciplina(),
                TextoUtils.capitalizar(disciplina.getNome())
        );
    }
}
