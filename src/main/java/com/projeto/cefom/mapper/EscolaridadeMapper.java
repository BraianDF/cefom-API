package com.projeto.cefom.mapper;

import com.projeto.cefom.dto.response.*;
import com.projeto.cefom.model.Adolescente;
import com.projeto.cefom.model.Escolaridade;
import com.projeto.cefom.utils.TextoUtils;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.Comparator;

@Component
public class EscolaridadeMapper {

    private final EscolaMapper escolaMapper;

    public EscolaridadeMapper(EscolaMapper escolaMapper) {
        this.escolaMapper = escolaMapper;
    }

    public EscolaridadeResponseDTO toResponseDTO(Adolescente adolescente, LocalDate data) {
        if (adolescente == null) return null;

        Escolaridade escolaridade = adolescente.getEscolaridades()
                .stream()
                .filter(e -> e.estaValidoEm(data))
                .max(Comparator.comparing(Escolaridade::getDataInicio))
                .orElse(null);

        return toResponseDTO(escolaridade);
    }

    public EscolaridadeResponseDTO toResponseDTO(Escolaridade escolaridade) {
        if (escolaridade == null) return null;

        return new EscolaridadeResponseDTO(
                escolaridade.getIdEscolaridade(),
                escolaMapper.toListarResponseDTO(escolaridade.getEscola()),
                escolaridade.getSerie(),
                escolaridade.getPeriodo(),
                escolaridade.getRaEscolar(),
                TextoUtils.capitalizar(escolaridade.getCurso()),
                escolaridade.getDataInicio(),
                escolaridade.getDataFim()
        );
    }

    public EscolaridadeListarResponseDTO toListarResponseDTO(Escolaridade escolaridade) {
        if (escolaridade == null) return null;

        return new EscolaridadeListarResponseDTO(
                escolaridade.getIdEscolaridade(),
                escolaridade.getDataInicio(),
                escolaridade.getDataFim(),
                escolaridade.getSerie()
        );
    }
}
