package com.projeto.cefom.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TaxaAdministrativaResponseDTO(
        Integer idTaxaAdministrativa,
        BigDecimal valorTaxa,
        LocalDate dataInicio,
        LocalDate dataFim
) {
}
