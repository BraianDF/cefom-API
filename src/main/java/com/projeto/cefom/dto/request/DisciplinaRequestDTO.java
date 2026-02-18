package com.projeto.cefom.dto.request;

import jakarta.validation.constraints.NotNull;

public record DisciplinaRequestDTO(
        @NotNull(message = "Nome é obrigatório.")
        String nome
) {
}
