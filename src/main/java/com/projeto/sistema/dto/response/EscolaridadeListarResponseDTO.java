package com.projeto.sistema.dto.response;

import com.projeto.sistema.enums.Serie;

import java.time.LocalDate;

public record EscolaridadeListarResponseDTO(
        Integer idEscolaridade,
        LocalDate dataInicio,
        LocalDate dataFim,
        Serie serie
) {
}
