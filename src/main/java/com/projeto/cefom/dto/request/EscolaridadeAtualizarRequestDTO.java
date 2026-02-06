package com.projeto.cefom.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EscolaridadeAtualizarRequestDTO(
        LocalDate dataModificacao,
        @Valid
        @NotNull(message = "Escolaridade é obrigatório.")
        EscolaridadeRequestDTO escolaridade
) {
}
