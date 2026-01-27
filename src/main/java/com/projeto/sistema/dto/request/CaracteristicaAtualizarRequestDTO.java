package com.projeto.sistema.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CaracteristicaAtualizarRequestDTO(
        LocalDate dataModificacao,
        @NotNull(message = "Caracteristica é obrigatório.")
        CaracteristicaRequestDTO caracteristica
) {
}
