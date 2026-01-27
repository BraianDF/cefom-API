package com.projeto.sistema.dto.request;

import com.projeto.sistema.enums.TipoSalario;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record SalarioRequestDTO(
        @NotNull(message = "Valor base é obrigatório.")
        BigDecimal valorBase,
        @NotNull(message = "Tipo de salário é obrigatório.")
        TipoSalario tipoSalario

) {
}
