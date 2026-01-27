package com.projeto.sistema.dto.request;

import com.projeto.sistema.enums.FinalizacaoInscricao;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record InscricaoEncerrarRequestDTO(
        LocalDate dataFinalizacao,
        @NotNull(message = "Motivo de finalização é obrigatório.")
        FinalizacaoInscricao motivoFinalizacao
) {
}
