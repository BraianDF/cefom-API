package com.projeto.sistema.dto.response;

import java.time.LocalDate;

public record FamiliarListarResponseDTO(
        Integer idFamiliar,
        LocalDate dataInicio,
        LocalDate dataFim,
        String nome,
        Boolean responsavel
) {
}
