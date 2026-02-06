package com.projeto.cefom.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CaracteristicaAtualizarRequestDTO(
        LocalDate dataModificacao,
        @Valid
        @NotNull(message = "Caracteristica é obrigatório.")
        CaracteristicaRequestDTO caracteristica
) {
}
