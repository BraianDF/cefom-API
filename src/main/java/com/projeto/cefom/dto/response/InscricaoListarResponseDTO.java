package com.projeto.cefom.dto.response;

import java.time.LocalDate;

public record InscricaoListarResponseDTO(
        Integer idInscricao,
        Integer numInscricao,
        LocalDate dataInscricao,
        LocalDate dataFinalizacao
) {
}
