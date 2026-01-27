package com.projeto.sistema.dto.response;

public record DocumentoResponsavelResponseDTO(
        Integer idDocumento,
        String cpf,
        String rg,
        String nis
) {
}
