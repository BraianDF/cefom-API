package com.projeto.cefom.dto.request;

import com.projeto.cefom.enums.EscolaridadeFamiliar;
import com.projeto.cefom.enums.EstadoCivil;
import com.projeto.cefom.enums.Naturalidade;
import com.projeto.cefom.enums.Parentesco;

import java.math.BigDecimal;

public record ResponsavelRequestDTO(
        String nome,
        Parentesco parentesco,
        Integer idade,
        EscolaridadeFamiliar escolaridade,
        String profissao,
        String localTrabalho,
        BigDecimal renda,
        Boolean reside,
        DocumentoResponsavelRequestDTO documento,
        Naturalidade naturalidade,
        EstadoCivil estadoCivil
) {
}
