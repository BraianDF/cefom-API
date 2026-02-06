package com.projeto.cefom.dto.response;

import com.projeto.cefom.enums.EscolaridadeFamiliar;
import com.projeto.cefom.enums.EstadoCivil;
import com.projeto.cefom.enums.Naturalidade;
import com.projeto.cefom.enums.Parentesco;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ResponsavelResponseDTO(
        Integer idFamiliar,
        String nome,
        Parentesco parentesco,
        Integer idade,
        EscolaridadeFamiliar escolaridade,
        String profissao,
        String localTrabalho,
        BigDecimal renda,
        Boolean reside,
        DocumentoResponsavelResponseDTO documento,
        Naturalidade naturalidade,
        EstadoCivil estadoCivil,
        LocalDate dataInicio,
        LocalDate dataFim
) {
}
