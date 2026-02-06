package com.projeto.cefom.dto.response;

public record EmpresaListarEntrevistaResponseDTO(
        Integer idEmpresa,
        String empresa,
        ResponsavelEmpresaListarResponseDTO ResponsavelEntevistas
) {
}
