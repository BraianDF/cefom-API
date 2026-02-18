package com.projeto.cefom.dto.response;

import com.projeto.cefom.enums.AvaliacaoAluno;

public record PresencaAvaliacaoResponseDTO(
        Integer idMatricula,
        AvaliacaoAluno avaliacao,
        String observacao
) {
}
