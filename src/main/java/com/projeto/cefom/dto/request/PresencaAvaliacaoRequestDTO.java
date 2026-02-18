package com.projeto.cefom.dto.request;

import com.projeto.cefom.enums.AvaliacaoAluno;
import jakarta.validation.constraints.NotNull;

public record PresencaAvaliacaoRequestDTO(
        @NotNull(message = "Id da Matrícula do aluno é obrigatório.")
        Integer idMatricula,
        @NotNull(message = "Avaliação do aluno é obrigatório.")
        AvaliacaoAluno avaliacao,
        String observacao
) {
}
