package com.projeto.cefom.novos.dto.response;

import com.projeto.cefom.novos.enums.MotivoJustificativa;

import java.time.LocalDate;

public record JustificativaFaltaListarResponseDTO(
        Integer idJustificativaFalta,
        LocalDate dataInicio,
        LocalDate dataFim,
        MotivoJustificativa motivo
) {
}
