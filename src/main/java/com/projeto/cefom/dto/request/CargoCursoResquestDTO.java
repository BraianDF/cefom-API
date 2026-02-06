package com.projeto.cefom.dto.request;

import jakarta.validation.constraints.NotNull;

public record CargoCursoResquestDTO(
        @NotNull(message = "Cargo é obrigatório.")
        Integer idCargo
) {
}
