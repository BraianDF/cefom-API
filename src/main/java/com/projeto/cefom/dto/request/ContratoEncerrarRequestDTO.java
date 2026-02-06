package com.projeto.cefom.dto.request;

import com.projeto.cefom.enums.DesligamentoMatricula;
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
