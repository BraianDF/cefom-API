package com.projeto.cefom.dto.response;

import java.time.LocalDate;

public record CursoListarResponseDTO (
        Integer idCurso,
        LocalDate dataInicio,
        LocalDate dataFim,
        String nomeCurso
) {
}
