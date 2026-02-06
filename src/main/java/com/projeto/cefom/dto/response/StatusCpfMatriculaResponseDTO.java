package com.projeto.cefom.dto.response;

import com.projeto.cefom.enums.StatusCpf;

public record StatusCpfMatriculaResponseDTO(
        StatusCpf status,
        MatriculaCriarResponseDTO dadosUltimaMatricula
) {
}
