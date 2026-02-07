package com.projeto.cefom.novos.dto.request;

import com.projeto.cefom.enums.Situacao;

public record TurmaRequestDTO(
        String nome,
        Situacao tipo
) {
}
