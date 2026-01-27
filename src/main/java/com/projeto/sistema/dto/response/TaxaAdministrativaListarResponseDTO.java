package com.projeto.sistema.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TaxaAdministrativaListarResponseDTO (
        Integer idTaxaAdministrativa,
        LocalDate dataInicio,
        LocalDate dataFim,
        BigDecimal valorTaxa
) {
}
