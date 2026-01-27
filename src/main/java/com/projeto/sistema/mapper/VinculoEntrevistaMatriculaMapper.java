package com.projeto.sistema.mapper;

import com.projeto.sistema.dto.response.VinculoEntrevistaMatriculaResponseDTO;
import com.projeto.sistema.model.VinculoEntrevistaMatricula;
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
