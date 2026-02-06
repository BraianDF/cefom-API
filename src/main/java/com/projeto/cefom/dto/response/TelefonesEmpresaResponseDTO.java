package com.projeto.cefom.dto.response;

public record TelefonesEmpresaResponseDTO(
        TelefoneResponseDTO telefonePrincipal,
        TelefoneResponseDTO telefoneExtra
) {
}
