package com.projeto.cefom.dto.response;

import com.projeto.cefom.enums.MotivoAfastamento;

import java.time.LocalDate;

public record AfastamentoListarResponseDTO(
        Integer idAfastamento,
        LocalDate dataInicio,
        LocalDate dataFim,
        MotivoAfastamento motivoAfastamento
) {
}
