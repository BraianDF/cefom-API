package com.projeto.sistema.dto.response;

import com.projeto.sistema.enums.DesligamentoMatricula;
import com.projeto.sistema.enums.SituacaoContrato;
import com.projeto.sistema.enums.TipoContratacao;

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
