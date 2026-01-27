package com.projeto.sistema.mapper;

import com.projeto.sistema.dto.response.*;
import com.projeto.sistema.model.Adolescente;
import com.projeto.sistema.model.Escolaridade;
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
        Escolaridade escolaridade = adolescente.getEscolaridades()
                .stream()
                .filter(e -> e.estaValidoEm(data))
                .max(Comparator.comparing(Escolaridade::getDataInicio))
                .orElse(null);

        return toResponseDTO(escolaridade);
    }

    public EscolaridadeResponseDTO toResponseDTO(Escolaridade escolaridade) {
        return new EscolaridadeResponseDTO(
                escolaridade != null ? escolaridade.getIdEscolaridade() : null,
                escolaridade != null && escolaridade.getEscola() != null ? escolaMapper.toListarResponseDTO(escolaridade.getEscola()) : null,
                escolaridade != null ? escolaridade.getSerie() : null,
                escolaridade != null ? escolaridade.getPeriodo() : null,
                escolaridade != null ? escolaridade.getRaEscolar() : null,
                escolaridade != null ? escolaridade.getCurso() : null,
                escolaridade != null ? escolaridade.getDataInicio() : null,
                escolaridade != null ? escolaridade.getDataFim() : null
        );
    }

    public EscolaridadeListarResponseDTO toListarResponseDTO(Escolaridade escolaridade) {
        return new EscolaridadeListarResponseDTO(
                escolaridade != null ? escolaridade.getIdEscolaridade() : null,
                escolaridade != null ? escolaridade.getDataInicio() : null,
                escolaridade != null ? escolaridade.getDataFim() : null,
                escolaridade != null ? escolaridade.getSerie() : null
        );
    }
}
