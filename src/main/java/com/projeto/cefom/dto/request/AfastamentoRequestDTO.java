package com.projeto.cefom.dto.request;

import com.projeto.cefom.enums.MotivoAfastamento;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AfastamentoRequestDTO (
        @NotNull(message = "Data de início é obrigatório.")
        LocalDate dataInicio,
        @NotNull(message = "Data de término é obrigatório.")
        LocalDate dataTermino,
        @NotNull(message = "Quantidade de dias é obrigatório.")
        Integer qtdeDias,
        @NotNull(message = "Motivo de afastamento é obrigatório.")
        MotivoAfastamento motivoAfastamento,
        String outroMotivoAfastamento,
        String observacao
) {
}
