package com.projeto.cefom.dto.response;

import com.projeto.cefom.enums.Serie;

import java.time.LocalDate;

public record EscolaridadeListarResponseDTO(
        Integer idEscolaridade,
        LocalDate dataInicio,
        LocalDate dataFim,
        Serie serie
) {
}
