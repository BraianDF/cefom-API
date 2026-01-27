package com.projeto.sistema.dto.response;

public record EmpresaListarEntrevistaResponseDTO(
        Integer idEmpresa,
        String empresa,
        ResponsavelEmpresaListarResponseDTO ResponsavelEntevistas
) {
}
