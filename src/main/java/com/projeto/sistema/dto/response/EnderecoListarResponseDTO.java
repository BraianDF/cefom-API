package com.projeto.sistema.dto.response;

import java.time.LocalDate;

public record EnderecoListarResponseDTO(
        Integer idEndereco,
        LocalDate dataInicio,
        LocalDate dataFim,
        String territorio
) {
}
