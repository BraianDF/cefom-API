package com.projeto.sistema.dto.response;

import com.projeto.sistema.enums.EscolaridadeFamiliar;
import com.projeto.sistema.enums.Parentesco;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FamiliarResponseDTO(
        Integer idFamiliar,
        String nome,
        Parentesco parentesco,
        Integer idade,
        EscolaridadeFamiliar escolaridade,
        String profissao,
        String localTrabalho,
        BigDecimal renda,
        LocalDate dataInicio,
        LocalDate dataFim
) {
}
