package com.projeto.sistema.dto.response;

import java.time.LocalDate;

public record JornadaTrabalhoListarResponseDTO(
        Integer idJornadaTrabalho,
        LocalDate dataInicio,
        LocalDate dataFim
) {
}
