package com.projeto.cefom.dto.request;

import com.projeto.cefom.enums.TipoSalario;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SalarioAtualizarRequestDTO(
        LocalDate dataModificacao,
        @NotNull(message = "Valor base é obrigatório.")
        BigDecimal valorBase,
        @NotNull(message = "Tipo de salário é obrigatório.")
        TipoSalario tipoSalario
) {
}
