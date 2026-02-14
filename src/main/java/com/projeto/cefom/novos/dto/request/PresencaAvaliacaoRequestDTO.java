package com.projeto.cefom.novos.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PresencaAvaliacaoRequestDTO(
        @NotNull(message = "Id da Matrícula do aluno é obrigatório.")
        Integer idMatricula,
        @NotNull(message = "Avaliação do aluno é obrigatório.")
        @Min(value = 0, message = "A avaliação mínima é 0.")
        @Max(value = 3, message = "A avaliação máxima é 3.")
        Integer avaliacao,
        String observacao
) {
}
