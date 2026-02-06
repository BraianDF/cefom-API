package com.projeto.cefom.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record InscricaoAtualizarRequestDTO(
        @NotNull(message = "Data de inscrição é obrigatório.")
        LocalDate dataInscricao,
        String observacao
) {
}
