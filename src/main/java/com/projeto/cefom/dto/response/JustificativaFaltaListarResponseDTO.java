package com.projeto.cefom.dto.response;

import com.projeto.cefom.enums.MotivoJustificativa;

import java.time.LocalDate;

public record JustificativaFaltaListarResponseDTO(
        Integer idJustificativaFalta,
        LocalDate dataInicio,
        LocalDate dataFim,
        MotivoJustificativa motivo
) {
}
