package com.projeto.sistema.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record EmailAtualizarRequestDTO(
        LocalDate dataModificacao,
        @NotNull(message = "Email do adolescente é obrigatório.")
        String emailAdolescente
) {
}
