package com.projeto.cefom.dto.response;

import java.time.LocalTime;

public record VinculoEntrevistaMatriculaResponseDTO(
        LocalTime horarioEntrevista,
        String situacao
) {
}
