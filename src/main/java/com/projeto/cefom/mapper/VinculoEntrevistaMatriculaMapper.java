package com.projeto.cefom.mapper;

import com.projeto.cefom.dto.response.VinculoEntrevistaMatriculaResponseDTO;
import com.projeto.cefom.model.VinculoEntrevistaMatricula;
import org.springframework.stereotype.Component;

@Component
public class VinculoEntrevistaMatriculaMapper {

    public VinculoEntrevistaMatriculaResponseDTO toResponseDTO(VinculoEntrevistaMatricula vinculo) {
        if (vinculo == null) return null;
        return new VinculoEntrevistaMatriculaResponseDTO(
                vinculo.getHorarioEntrevista(),
                vinculo.getSituacao()
        );
    }
}
