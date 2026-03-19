package com.projeto.cefom.mapper;

import com.projeto.cefom.dto.response.SalaAulaResponseDTO;
import com.projeto.cefom.model.SalaAula;
import com.projeto.cefom.utils.TextoUtils;
import org.springframework.stereotype.Component;

@Component
public class SalaAulaMapper {
    public SalaAulaResponseDTO toResponseDTO(SalaAula salaAula) {
        if (salaAula == null) return null;
        return new SalaAulaResponseDTO(
                salaAula.getIdSalaAula(),
                TextoUtils.capitalizar(salaAula.getNome())
        );
    }
}
