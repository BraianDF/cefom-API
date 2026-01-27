package com.projeto.sistema.mapper;

import com.projeto.sistema.dto.response.AfastamentoListarResponseDTO;
import com.projeto.sistema.dto.response.AfastamentoResponseDTO;
import com.projeto.sistema.model.Afastamento;
import com.projeto.sistema.model.Contrato;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Comparator;

@Component
public class AfastamentoMapper {

    public AfastamentoResponseDTO toResponseDTO (Contrato contrato, LocalDate data) {
        Afastamento afastamento = contrato.getAfastamentos()
                .stream()
                .filter(e -> e.estaValidoEm(data))
                .max(Comparator.comparing(Afastamento::getDataInicio))
                .orElse(null);

        return toResponseDTO(afastamento);
    }

    public AfastamentoResponseDTO toResponseDTO(Afastamento afastamento) {
        if (afastamento == null) return null;

        return new AfastamentoResponseDTO(
                afastamento.getIdAfastamento(),
                afastamento.getDataInicio(),
                afastamento.getDataFim(),
                afastamento.getQtdeDias(),
                afastamento.getMotivoAfastamento(),
                afastamento.getOutroMotivoAfastamento(),
                afastamento.getObservacao()
        );
    }

    public AfastamentoListarResponseDTO toListarResponseDTO(Afastamento afastamento) {
        if (afastamento == null) return null;

        return new AfastamentoListarResponseDTO(
                afastamento.getIdAfastamento(),
                afastamento.getDataInicio(),
                afastamento.getDataFim(),
                afastamento.getMotivoAfastamento()
        );
    }
}
