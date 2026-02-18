package com.projeto.cefom.dto.request;

import jakarta.validation.constraints.NotNull;

public record SalaAulaRequestDTO(
        @NotNull(message = "Nome é obrigatório.")
        String nome
) {
}
