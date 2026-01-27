package com.projeto.sistema.dto.response;

import com.projeto.sistema.enums.Beneficio;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosSocialResponseDTO(
        Integer idDadosSocial,
        Boolean comportamentoBoolean,
        String comportamento,
        Boolean encaminhamentoBoolean,
        String encaminhamento,
        Boolean beneficioBoolean,
        Beneficio beneficio,
        BigDecimal beneficioValor,
        Boolean problemaSaudeBoolean,
        String problemaSaude,
        Boolean medicamentoBoolean,
        String medicamento,
        Boolean entidadeBoolean,
        String entidade,
        BigDecimal rendaFamiliar,
        String composicaoFamiliar,
        LocalDate dataInicio,
        LocalDate dataFim

) {
}
