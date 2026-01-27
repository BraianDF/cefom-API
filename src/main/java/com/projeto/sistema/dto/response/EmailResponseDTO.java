package com.projeto.sistema.dto.response;

import com.projeto.sistema.enums.TitularContato;

import java.time.LocalDate;

public record EmailResponseDTO(
        Integer idEmail,
        String email,
        TitularContato titular,
        LocalDate dataInicio,
        LocalDate dataFim
) {
}
