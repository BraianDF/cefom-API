package com.projeto.cefom.novos.dto.response;

import java.time.LocalDate;

public record FaltaTrabalhoListarResponseDTO(
        Integer idFaltaTrabalho,
        LocalDate dataFalta,
        Boolean justificada
) {
}
