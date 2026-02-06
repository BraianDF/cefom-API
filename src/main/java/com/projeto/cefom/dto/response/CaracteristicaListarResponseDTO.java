package com.projeto.cefom.dto.response;

import java.time.LocalDate;

public record CaracteristicaListarResponseDTO(
        Integer idCaracteristica,
        LocalDate dataInicio,
        LocalDate dataFim
) {
}
