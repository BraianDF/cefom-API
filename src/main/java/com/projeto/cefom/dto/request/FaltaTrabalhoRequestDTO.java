package com.projeto.cefom.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record FaltaTrabalhoRequestDTO(
        @NotNull(message = "Data é obrigatória.")
        LocalDate dataFalta
) {
}
