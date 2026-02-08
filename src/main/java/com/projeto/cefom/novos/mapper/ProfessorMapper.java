package com.projeto.cefom.novos.mapper;

import com.projeto.cefom.novos.dto.response.DisciplinaResponseDTO;
import com.projeto.cefom.novos.dto.response.ProfessorResponseDTO;
import com.projeto.cefom.novos.model.Disciplina;
import com.projeto.cefom.novos.model.Professor;
import org.springframework.stereotype.Component;

@Component
public class ProfessorMapper {
    public ProfessorResponseDTO toResponseDTO(Professor professor) {
        if (professor == null) return null;
        return new ProfessorResponseDTO(
                professor.getIdProfessor(),
                professor.getNome()
        );
    }
}
