package com.projeto.cefom.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record FamiliaresAtualizarRequestDTO(
        LocalDate dataModificacao,
        @Valid
        @NotNull(message = "Familiares são obrigatórios.")
        FamiliaresRequestDTO familiares
) {
}
