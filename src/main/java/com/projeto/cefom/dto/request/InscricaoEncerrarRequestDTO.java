package com.projeto.cefom.dto.request;

import com.projeto.cefom.enums.FinalizacaoInscricao;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record InscricaoEncerrarRequestDTO(
        LocalDate dataFinalizacao,
        @NotNull(message = "Motivo de finalização é obrigatório.")
        FinalizacaoInscricao motivoFinalizacao
) {
}
