package com.projeto.cefom.mapper;

import com.projeto.cefom.dto.response.TerritorioListarResponseDTO;
import com.projeto.cefom.dto.response.TerritorioResponseDTO;
import com.projeto.cefom.model.Territorio;
import com.projeto.cefom.utils.TextoUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TerritorioMapper {

    public TerritorioResponseDTO toResponseDTO(Territorio territorio) {
        if (territorio == null) return null;

        Set<String> bairros = Optional.ofNullable(territorio.getBairros())
                .orElse(Collections.emptySet())
                .stream()
                .filter(Objects::nonNull)
                .map(TextoUtils::capitalizar)
                .collect(Collectors.toSet());

        return new TerritorioResponseDTO(
                territorio.getIdTerritorio(),
                TextoUtils.capitalizar(territorio.getResultado()),
                bairros
        );
    }

    public TerritorioListarResponseDTO toListarResponseDTO(Territorio territorio) {
        if (territorio == null) return null;
        return new TerritorioListarResponseDTO(
                territorio.getIdTerritorio(),
                TextoUtils.capitalizar(territorio.getResultado())
        );
    }
}
