package com.projeto.sistema.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EscolaridadeAtualizarRequestDTO(
        LocalDate dataModificacao,
        @NotNull(message = "Escolaridade é obrigatório.")
        EscolaridadeRequestDTO escolaridade
) {
}
