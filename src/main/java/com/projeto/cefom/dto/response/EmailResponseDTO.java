package com.projeto.cefom.dto.response;

import com.projeto.cefom.enums.TitularContato;

import java.time.LocalDate;

public record EmailResponseDTO(
        Integer idEmail,
        String email,
        TitularContato titular,
        LocalDate dataInicio,
        LocalDate dataFim
) {
}
