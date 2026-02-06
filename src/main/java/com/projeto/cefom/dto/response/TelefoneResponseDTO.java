package com.projeto.cefom.dto.response;

import com.projeto.cefom.enums.TitularContato;

import java.time.LocalDate;

public record TelefoneResponseDTO(
        Integer idTelefone,
        String numero,
        TitularContato titular,
        LocalDate dataInicio,
        LocalDate dataFim
) {
}
