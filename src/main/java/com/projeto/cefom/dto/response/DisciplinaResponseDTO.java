package com.projeto.cefom.dto.response;

import jakarta.validation.constraints.NotNull;

public record DisciplinaResponseDTO(
        Integer idDisciplina,
        String nome
) {
}
