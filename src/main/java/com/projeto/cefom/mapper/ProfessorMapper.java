package com.projeto.cefom.mapper;

import com.projeto.cefom.dto.response.ProfessorResponseDTO;
import com.projeto.cefom.model.Professor;
import com.projeto.cefom.utils.TextoUtils;
import org.springframework.stereotype.Component;

@Component
public class ProfessorMapper {
    public ProfessorResponseDTO toResponseDTO(Professor professor) {
        if (professor == null) return null;
        return new ProfessorResponseDTO(
                professor.getIdProfessor(),
                TextoUtils.capitalizar(professor.getNome())
        );
    }
}
