package com.projeto.cefom.dto.response;

import java.time.LocalDate;

public record MatriculaListarResponseDTO (
        Integer idMatricula,
        Integer numMatricula,
        LocalDate dataMatricula,
        LocalDate dataDesligamento
) {
}
