package com.projeto.sistema.dto.response;

import com.projeto.sistema.enums.TitularContato;

import java.time.LocalDate;

public record TelefoneResponseDTO(
        Integer idTelefone,
        String numero,
        TitularContato titular,
        LocalDate dataInicio,
        LocalDate dataFim
) {
}
