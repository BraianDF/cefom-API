package com.projeto.sistema.dto.response;

import java.time.LocalDate;

public record MatriculaListarResponseDTO (
        Integer idMatricula,
        Integer numMatricula,
        LocalDate dataMatricula,
        LocalDate dataDesligamento
) {
}
