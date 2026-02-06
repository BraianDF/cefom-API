package com.projeto.cefom.dto.request;

import jakarta.validation.Valid;

import java.util.List;

public record FamiliaresRequestDTO(
        @Valid
        ResponsavelRequestDTO responsavel,
        @Valid
        List<FamiliarRequestDTO> familiares
) {
}
