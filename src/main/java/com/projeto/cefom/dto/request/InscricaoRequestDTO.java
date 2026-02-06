package com.projeto.cefom.dto.request;

import java.time.LocalDate;

public record InscricaoRequestDTO(
        LocalDate dataInscricao,
        String observacao
) {
}
