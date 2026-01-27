package com.projeto.sistema.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TaxaAdministrativaResponseDTO(
        Integer idTaxaAdministrativa,
        BigDecimal valorTaxa,
        LocalDate dataInicio,
        LocalDate dataFim
) {
}
