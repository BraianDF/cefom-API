package com.projeto.sistema.dto.request;

import java.time.LocalDate;

public record InscricaoRequestDTO(
        LocalDate dataInscricao,
        String observacao
) {
}
