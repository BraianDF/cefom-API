package com.projeto.sistema.dto.response;

public record ResponsaveisEmpresaResponseDTO(
        ResponsavelEmpresaResponseDTO ResponsavelEmpresa,
        ResponsavelEmpresaResponseDTO ResponsavelAprendizes,
        ResponsavelEmpresaResponseDTO ResponsavelEntevistas
) {
}
