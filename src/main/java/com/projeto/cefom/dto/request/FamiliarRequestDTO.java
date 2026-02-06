package com.projeto.cefom.dto.request;

import com.projeto.cefom.enums.EscolaridadeFamiliar;
import com.projeto.cefom.enums.Parentesco;

import java.math.BigDecimal;

public record FamiliarRequestDTO(
        String nome,
        Parentesco parentesco,
        Integer idade,
        EscolaridadeFamiliar escolaridade,
        String profissao,
        String localTrabalho,
        BigDecimal renda
) {
}
