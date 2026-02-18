package com.projeto.cefom.dto.response;

import com.projeto.cefom.enums.MotivoJustificativa;

import java.time.LocalDate;

public record JustificativaFaltaResponseDTO(
        Integer idJustificativaFalta,
        MotivoJustificativa motivo,
        LocalDate dataInicio,
        LocalDate dataFim,
        Integer qtdeDias,
        String observacao
) {
}
