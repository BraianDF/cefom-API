package com.projeto.sistema.dto.response;

import com.projeto.sistema.enums.FinalizacaoInscricao;

import java.time.LocalDate;

public record InscricaoResponseDTO(
        Integer idInscricao,
        Integer numInscricao,
        String observacao,
        LocalDate dataInscricao,
        LocalDate dataFinalizacao,
        FinalizacaoInscricao motivoFinalizacao
) {
}
