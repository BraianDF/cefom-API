package com.projeto.cefom.mapper;

import com.projeto.cefom.dto.response.AfastamentoListarResponseDTO;
import com.projeto.cefom.dto.response.AfastamentoResponseDTO;
import com.projeto.cefom.model.Afastamento;
import com.projeto.cefom.model.Contrato;
import com.projeto.cefom.utils.TextoUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Comparator;

@Component
public class AfastamentoMapper {

    public AfastamentoResponseDTO toResponseDTO (Contrato contrato, LocalDate data) {
        if (contrato == null) return null;

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
                TextoUtils.capitalizar(afastamento.getOutroMotivoAfastamento()),
                TextoUtils.capitalizarPrimeiraLetra(afastamento.getObservacao())
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
