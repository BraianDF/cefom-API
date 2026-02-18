package com.projeto.cefom.dto.request;

import com.projeto.cefom.enums.MotivoJustificativa;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record JustificativaFaltaRequestDTO(
        @NotNull(message = "Motivo é obrigatório.")
        MotivoJustificativa motivo,
        @NotNull(message = "Data de inicio é obrigatória.")
        LocalDate dataInicio,
        @NotNull(message = "Quantidade de dias é obrigatório.")
        @Positive(message = "Quantidade de dias deve ser maior que zero.")
        Integer qtdeDias,
        String observacao
) {
}
