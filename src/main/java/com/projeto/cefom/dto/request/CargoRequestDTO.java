package com.projeto.cefom.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CargoRequestDTO(
        @NotBlank(message = "Função é obrigatório.")
        String funcao,
        @NotBlank(message = "CBO é obrigatório.")
        String cbo
) {
}
