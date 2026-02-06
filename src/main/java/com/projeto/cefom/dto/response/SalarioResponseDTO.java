package com.projeto.cefom.dto.response;

import com.projeto.cefom.enums.TipoSalario;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SalarioResponseDTO(
        Integer idSalario,
        BigDecimal valorBase,
        TipoSalario tipoSalario,
        LocalDate dataInicio,
        LocalDate dataFim
) {
}
