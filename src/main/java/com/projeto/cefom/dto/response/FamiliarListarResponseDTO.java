package com.projeto.cefom.dto.response;

import java.time.LocalDate;

public record FamiliarListarResponseDTO(
        Integer idFamiliar,
        LocalDate dataInicio,
        LocalDate dataFim,
        String nome,
        Boolean responsavel
) {
}
