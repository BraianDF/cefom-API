package com.projeto.cefom.novos.dto.response;

import java.time.LocalDate;

public record FaltaTrabalhoResponseDTO(
        Integer idFaltaTrabalho,
        LocalDate dataFalta,
        Boolean justificada
) {
}
