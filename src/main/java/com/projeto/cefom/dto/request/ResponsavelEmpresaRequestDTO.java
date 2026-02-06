package com.projeto.cefom.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ResponsavelEmpresaRequestDTO(
        @NotBlank(message = "Responsável pela empresa é obrigatório.")
        String responsavelEmpresa,
        String responsavelAprendizes,
        String responsavelEntevistas
) {
}
