package com.projeto.cefom.dto.response;

import com.projeto.cefom.enums.MotivoAfastamento;

import java.time.LocalDate;

public record AfastamentoResponseDTO(
        Integer idAfastamento,
        LocalDate dataInicio,
        LocalDate dataTermino,
        Integer qtdeDias,
        MotivoAfastamento motivoAfastamento,
        String outroMotivoAfastamento,
        String observacao
) {
}
