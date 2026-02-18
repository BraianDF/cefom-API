package com.projeto.cefom.dto.response;

import java.util.List;

public record ChamadaAvaliacaoResponseDTO(
        List<PresencaAvaliacaoResponseDTO> alunos
) {
}
