package com.projeto.cefom.dto.response;

import java.time.LocalDate;

public record EmailListarResponseDTO(
        Integer idEmail,
        LocalDate dataInicio,
        LocalDate dataFim,
        String email
) {
}
