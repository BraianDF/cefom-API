package com.projeto.cefom.novos.mapper;

import com.projeto.cefom.novos.dto.response.DisciplinaResponseDTO;
import com.projeto.cefom.novos.dto.response.SalaAulaResponseDTO;
import com.projeto.cefom.novos.model.Disciplina;
import com.projeto.cefom.novos.model.SalaAula;
import org.springframework.stereotype.Component;

@Component
public class DisciplinaMapper {
    public DisciplinaResponseDTO toResponseDTO(Disciplina disciplina) {
        if (disciplina == null) return null;
        return new DisciplinaResponseDTO(
                disciplina.getIdDisciplina(),
                disciplina.getNome()
        );
    }
}
