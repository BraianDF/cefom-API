package com.projeto.cefom.dto.response;

import com.projeto.cefom.enums.StatusCpf;

public record StatusCpfInscricaoResponseDTO(
        StatusCpf status,
        InscricaoCriarResponseDTO dadosUltimaInscricao
) {
}
