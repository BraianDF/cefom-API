package com.projeto.cefom.novos.dto.request;

import jakarta.validation.constraints.NotNull;

public record ProfessorRequestDTO(
        @NotNull(message = "Nome é obrigatório.")
        String nome
) {
}
