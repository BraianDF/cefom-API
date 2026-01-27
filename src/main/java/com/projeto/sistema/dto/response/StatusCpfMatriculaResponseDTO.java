package com.projeto.sistema.dto.response;

import com.projeto.sistema.enums.StatusCpf;

public record StatusCpfMatriculaResponseDTO(
        StatusCpf status,
        MatriculaCriarResponseDTO dadosUltimaMatricula
) {
}
