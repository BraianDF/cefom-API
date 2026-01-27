package com.projeto.sistema.dto.response;

import com.projeto.sistema.enums.StatusCpf;

public record StatusCpfInscricaoResponseDTO(
        StatusCpf status,
        InscricaoCriarResponseDTO dadosUltimaInscricao
) {
}
