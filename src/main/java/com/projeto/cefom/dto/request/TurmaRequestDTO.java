package com.projeto.cefom.dto.request;

import com.projeto.cefom.enums.Situacao;
import jakarta.validation.constraints.NotNull;

public record TurmaRequestDTO(
        @NotNull(message = "Nome da turma é obrigatório.")
        String nome,
        @NotNull(message = "Tipo da turma é obrigatório.")
        Situacao tipo
) {
}
