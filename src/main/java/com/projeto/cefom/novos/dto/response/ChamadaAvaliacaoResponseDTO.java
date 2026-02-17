package com.projeto.cefom.novos.dto.response;

import java.util.List;

public record ChamadaAvaliacaoResponseDTO(
        List<PresencaAvaliacaoResponseDTO> alunos
) {
}
