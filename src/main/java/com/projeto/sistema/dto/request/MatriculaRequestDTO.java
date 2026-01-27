package com.projeto.sistema.dto.request;

import com.projeto.sistema.enums.SituacaoMatricula;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record MatriculaRequestDTO(
        LocalDate dataMatricula,
        @NotNull(message = "Situação de matrícula é obrigatório.")
        SituacaoMatricula situacaoMatricula
) {
}
