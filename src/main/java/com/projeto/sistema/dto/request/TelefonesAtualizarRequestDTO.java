package com.projeto.sistema.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TelefonesAtualizarRequestDTO(
        LocalDate dataModificacao,
        @Valid
        @NotNull(message = "Telefones são obrigatórios.")
        TelefonesRequestDTO telefones
) {
}
