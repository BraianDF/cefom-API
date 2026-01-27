package com.projeto.sistema.dto.response;

import java.util.List;

public record FamiliaresResponseDTO(
        ResponsavelResponseDTO responsavel,
        List<FamiliarResponseDTO> familiares
) {
}
