package com.projeto.cefom.dto.response;

import com.projeto.cefom.enums.Situacao;

public record TurmaListarResponseDTO(
        Integer idTurma,
        String nome,
        Situacao tipo
) {
}
