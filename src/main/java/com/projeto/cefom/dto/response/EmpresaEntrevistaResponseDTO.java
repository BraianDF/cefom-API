package com.projeto.cefom.dto.response;

public record EmpresaEntrevistaResponseDTO(
        EmpresaResponseDTO empresa,
        ResponsavelEmpresaResponseDTO responsavelEntrevista
) {
}
