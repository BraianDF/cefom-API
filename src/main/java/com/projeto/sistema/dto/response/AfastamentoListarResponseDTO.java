package com.projeto.sistema.dto.response;

import com.projeto.sistema.enums.MotivoAfastamento;

import java.time.LocalDate;

public record AfastamentoListarResponseDTO(
        Integer idAfastamento,
        LocalDate dataInicio,
        LocalDate dataFim,
        MotivoAfastamento motivoAfastamento
) {
}
