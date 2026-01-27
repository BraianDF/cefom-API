package com.projeto.sistema.dto.request;

import com.projeto.sistema.enums.Beneficio;

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
