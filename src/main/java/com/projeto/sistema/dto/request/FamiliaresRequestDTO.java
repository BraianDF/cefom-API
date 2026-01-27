package com.projeto.sistema.dto.request;

import java.util.List;

public record FamiliaresRequestDTO(
        ResponsavelRequestDTO responsavel,
        List<FamiliarRequestDTO> familiares
) {
}
