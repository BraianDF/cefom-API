package com.projeto.cefom.dto.request;

import com.projeto.cefom.enums.DesligamentoMatricula;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record MatriculaEncerrarRequestDTO(
        LocalDate dataDesligamento,
        @NotNull(message = "Motivo de desligamento é obrigatório.")
        DesligamentoMatricula MotivoDesligamento
) {
}
