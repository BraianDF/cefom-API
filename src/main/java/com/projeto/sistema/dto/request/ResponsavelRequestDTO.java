package com.projeto.sistema.dto.request;

import com.projeto.sistema.enums.EscolaridadeFamiliar;
import com.projeto.sistema.enums.EstadoCivil;
import com.projeto.sistema.enums.Naturalidade;
import com.projeto.sistema.enums.Parentesco;

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
