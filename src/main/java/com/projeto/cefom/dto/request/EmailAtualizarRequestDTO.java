package com.projeto.cefom.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record EmailAtualizarRequestDTO(
        LocalDate dataModificacao,
        @Valid
        @NotNull(message = "Email do adolescente é obrigatório.")
        String emailAdolescente
) {
}
