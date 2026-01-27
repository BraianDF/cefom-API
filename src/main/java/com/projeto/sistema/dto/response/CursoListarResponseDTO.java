package com.projeto.sistema.dto.response;

import java.time.LocalDate;

public record CursoListarResponseDTO (
        Integer idCurso,
        LocalDate dataInicio,
        LocalDate dataFim,
        String nomeCurso
) {
}
