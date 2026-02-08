package com.projeto.cefom.novos.mapper;

import com.projeto.cefom.novos.dto.response.SalaAulaResponseDTO;
import com.projeto.cefom.novos.model.SalaAula;
import org.springframework.stereotype.Component;

@Component
public class SalaAulaMapper {
    public SalaAulaResponseDTO toResponseDTO(SalaAula salaAula) {
        if (salaAula == null) return null;
        return new SalaAulaResponseDTO(
                salaAula.getIdSalaAula(),
                salaAula.getNome()
        );
    }
}
