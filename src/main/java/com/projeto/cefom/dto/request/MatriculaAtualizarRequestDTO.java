package com.projeto.cefom.dto.request;

import com.projeto.cefom.enums.SituacaoMatricula;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record MatriculaAtualizarRequestDTO(
        @NotNull(message = "Situação de matrícula é obrigatório.")
        SituacaoMatricula situacaoMatricula,
        @NotNull(message = "Data de matrícula é obrigatório.")
        LocalDate dataMatricula
) {
}
