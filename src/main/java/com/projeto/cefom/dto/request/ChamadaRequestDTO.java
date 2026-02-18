package com.projeto.cefom.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ChamadaRequestDTO(
        @Valid
        @NotNull(message = "Lista de alunos é obrigatório.")
        List<PresencaRequestDTO> alunos
) {
}
