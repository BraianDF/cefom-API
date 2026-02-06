package com.projeto.cefom.dto.response;

import java.util.List;

public record FamiliaresResponseDTO(
        ResponsavelResponseDTO responsavel,
        List<FamiliarResponseDTO> familiares
) {
}
