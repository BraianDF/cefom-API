package com.projeto.sistema.dto.request;

import com.projeto.sistema.enums.DesligamentoMatricula;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record MatriculaEncerrarRequestDTO(
        LocalDate dataDesligamento,
        @NotNull(message = "Motivo de desligamento é obrigatório.")
        DesligamentoMatricula MotivoDesligamento
) {
}
