package com.projeto.sistema.dto.response;

public record EmpresaEntrevistaResponseDTO(
        EmpresaResponseDTO empresa,
        ResponsavelEmpresaResponseDTO responsavelEntrevista
) {
}
