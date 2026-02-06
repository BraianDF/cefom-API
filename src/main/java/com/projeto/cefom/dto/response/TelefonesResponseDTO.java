package com.projeto.cefom.dto.response;

public record TelefonesResponseDTO(
        TelefoneResponseDTO telefoneAdolescente,
        TelefoneResponseDTO telefoneResponsavel,
        TelefoneResponseDTO telefoneExtra
) {
}
