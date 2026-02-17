package com.projeto.cefom.novos.dto.response;

import com.projeto.cefom.novos.enums.AvaliacaoAluno;

public record PresencaAvaliacaoResponseDTO(
        Integer idMatricula,
        AvaliacaoAluno avaliacao,
        String observacao
) {
}
