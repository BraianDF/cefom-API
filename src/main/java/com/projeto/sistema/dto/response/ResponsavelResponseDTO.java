package com.projeto.sistema.dto.response;

import com.projeto.sistema.enums.EscolaridadeFamiliar;
import com.projeto.sistema.enums.EstadoCivil;
import com.projeto.sistema.enums.Naturalidade;
import com.projeto.sistema.enums.Parentesco;

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
