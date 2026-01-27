package com.projeto.sistema.dto.request;

import com.projeto.sistema.enums.DesligamentoMatricula;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ContratoEncerrarRequestDTO(
        LocalDate dataFim,
        @NotNull(message = "Motivo de desligamento é obrigatório.")
        DesligamentoMatricula motivoDesligamento,
        @NotNull(message = "Efetivado ou não é obrigatório.")
        Boolean efetivado
) {
}
