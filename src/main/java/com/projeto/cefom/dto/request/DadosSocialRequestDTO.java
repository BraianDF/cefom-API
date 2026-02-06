package com.projeto.cefom.dto.request;

import com.projeto.cefom.enums.Beneficio;

import java.math.BigDecimal;

public record DadosSocialRequestDTO(
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
        String entidade
) {
}
