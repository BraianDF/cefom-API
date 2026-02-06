package com.projeto.cefom.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record ContratoRequestDTO(
        @Valid
        @NotNull(message = "Contrato é obrigatório.")
        ContratoCriarRequestDTO contrato,
        @Valid
        @NotNull(message = "Salário é obrigatório.")
        SalarioRequestDTO salario,
        @Valid
        @NotNull(message = "Jornada de trabalho é obrigatório.")
        JornadaTrabalhoRequestDTO jornadaTrabalho
) {
}
