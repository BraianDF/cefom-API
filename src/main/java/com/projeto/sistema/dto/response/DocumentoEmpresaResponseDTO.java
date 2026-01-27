package com.projeto.sistema.dto.response;

public record DocumentoEmpresaResponseDTO(
        Integer idDocumento,
        String documento //cpf ou cnpj
) {
}
