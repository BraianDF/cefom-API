package com.projeto.cefom.dto.response;

import java.util.Set;

public record TerritorioPendenciasResponseDTO(
        Integer enderecosSemTerritorio,
        Set<String> bairrosSemTerritorio
) {
}
