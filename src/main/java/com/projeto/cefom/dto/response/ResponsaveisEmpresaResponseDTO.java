package com.projeto.cefom.dto.response;

public record ResponsaveisEmpresaResponseDTO(
        ResponsavelEmpresaResponseDTO ResponsavelEmpresa,
        ResponsavelEmpresaResponseDTO ResponsavelAprendizes,
        ResponsavelEmpresaResponseDTO ResponsavelEntevistas
) {
}
