package com.projeto.sistema.dto.response;

import java.time.LocalDate;

public record TelefoneListarResponseDTO(
        Integer idTelefone,
        LocalDate dataInicio,
        LocalDate dataFim,
        String numero
) {
}
