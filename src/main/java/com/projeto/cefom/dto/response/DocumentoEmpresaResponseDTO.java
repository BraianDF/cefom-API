package com.projeto.cefom.dto.response;

public record DocumentoEmpresaResponseDTO(
        Integer idDocumento,
        String documento //cpf ou cnpj
) {
}
