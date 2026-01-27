package com.projeto.sistema.dto.response;

public record TelefonesResponseDTO(
        TelefoneResponseDTO telefoneAdolescente,
        TelefoneResponseDTO telefoneResponsavel,
        TelefoneResponseDTO telefoneExtra
) {
}
