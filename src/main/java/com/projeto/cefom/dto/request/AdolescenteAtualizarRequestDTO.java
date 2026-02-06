package com.projeto.cefom.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AdolescenteAtualizarRequestDTO(
        LocalDate dataModificacao,
        @Valid
        @NotNull(message = "Adolescente é obrigatório.")
        AdolescenteMatriculaRequestDTO adolescente
) {
}
