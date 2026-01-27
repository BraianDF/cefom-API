package com.projeto.sistema.dto.request;

import jakarta.validation.constraints.NotNull;

public record ContratoRequestDTO(
        @NotNull(message = "Contrato é obrigatório.")
        ContratoCriarRequestDTO contrato,
        @NotNull(message = "Salário é obrigatório.")
        SalarioRequestDTO salario,
        @NotNull(message = "Jornada de trabalho é obrigatório.")
        JornadaTrabalhoRequestDTO jornadaTrabalho
) {
}
