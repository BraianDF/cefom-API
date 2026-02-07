package com.projeto.cefom.novos.dto.response;

import com.projeto.cefom.enums.Situacao;

public record TurmaResponseDTO(
        Integer idTurma,
        String nome,
        Situacao tipo
) {
}
