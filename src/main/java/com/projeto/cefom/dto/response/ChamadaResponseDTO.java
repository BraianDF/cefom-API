package com.projeto.cefom.dto.response;

import java.util.List;

public record ChamadaResponseDTO(
        List<PresencaResponseDTO> alunos
) {
}
