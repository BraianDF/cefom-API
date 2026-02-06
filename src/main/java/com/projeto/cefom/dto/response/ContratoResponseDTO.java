package com.projeto.cefom.dto.response;

import com.projeto.cefom.enums.DesligamentoMatricula;
import com.projeto.cefom.enums.SituacaoContrato;
import com.projeto.cefom.enums.TipoContratacao;

import java.time.LocalDate;

public record ContratoResponseDTO(
        Integer idContrato,
        SituacaoContrato situacaoContrato,
        LocalDate dataInicio,
        LocalDate dataTermino,
        LocalDate dataFim,
        DesligamentoMatricula desligamento,
        Boolean efetivado,
        TipoContratacao tipoContratacao,
        EmpresaResponseDTO empresa,
        CargoResponseDTO cargo,
        SalarioResponseDTO salario,
        JornadaTrabalhoResponseDTO jornadaTrabalho
) {
}
