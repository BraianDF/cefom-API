package com.projeto.cefom.dto.request;

import com.projeto.cefom.enums.SituacaoMatricula;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record MatriculaRequestDTO(
        LocalDate dataMatricula,
        @NotNull(message = "Situação de matrícula é obrigatório.")
        SituacaoMatricula situacaoMatricula
) {
}
