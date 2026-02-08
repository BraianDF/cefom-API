package com.projeto.cefom.novos.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AlunoParticipacaoRequestDTO(
        LocalDate dataModificacao,
        @NotNull(message = "Matricula é obrigatório.")
        Integer idMatricula
) {
}
