package com.projeto.sistema.dto.response;

import java.time.LocalDate;

public record InscricaoListarResponseDTO(
        Integer idInscricao,
        Integer numInscricao,
        LocalDate dataInscricao,
        LocalDate dataFinalizacao
) {
}
