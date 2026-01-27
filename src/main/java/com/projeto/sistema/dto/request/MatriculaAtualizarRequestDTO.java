package com.projeto.sistema.dto.request;

import com.projeto.sistema.enums.SituacaoMatricula;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record MatriculaAtualizarRequestDTO(
        @NotNull(message = "Situação de matrícula é obrigatório.")
        SituacaoMatricula situacaoMatricula,
        @NotNull(message = "Data de matrícula é obrigatório.")
        LocalDate dataMatricula
) {
}
