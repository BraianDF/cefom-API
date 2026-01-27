package com.projeto.sistema.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SalarioListarResponseDTO(
        Integer idSalario,
        LocalDate dataInicio,
        LocalDate dataFim,
        BigDecimal valorBase
) {
}
