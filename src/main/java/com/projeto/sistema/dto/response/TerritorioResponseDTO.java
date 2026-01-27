package com.projeto.sistema.dto.response;

import java.util.Set;

public record TerritorioResponseDTO(
        Integer idTerritorio,
        String territorio,
        Set<String> bairros
) {
}
