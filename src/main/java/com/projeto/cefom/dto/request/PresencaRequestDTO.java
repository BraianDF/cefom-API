package com.projeto.cefom.dto.request;

import jakarta.validation.constraints.NotNull;

public record PresencaRequestDTO(
        @NotNull(message = "Id da Matrícula do aluno é obrigatório.")
        Integer idMatricula,
        @NotNull(message = "Presente ou Ausente é obrigatório.")
        Boolean presente
) {
}
