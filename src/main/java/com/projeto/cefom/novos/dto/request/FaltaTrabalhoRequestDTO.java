package com.projeto.cefom.novos.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record FaltaTrabalhoRequestDTO(
        @NotNull(message = "Data é obrigatória.")
        LocalDate dataFalta
) {
}
