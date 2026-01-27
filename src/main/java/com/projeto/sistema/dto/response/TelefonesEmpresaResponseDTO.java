package com.projeto.sistema.dto.response;

import com.projeto.sistema.dto.response.TelefoneResponseDTO;

public record TelefonesEmpresaResponseDTO(
        TelefoneResponseDTO telefonePrincipal,
        TelefoneResponseDTO telefoneExtra
) {
}
