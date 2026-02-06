package com.projeto.cefom.dto.request;

import com.projeto.cefom.enums.Periodo;
import com.projeto.cefom.enums.Serie;

public record EscolaridadeRequestDTO(
        Integer idEscola,
        Serie serie,
        Periodo periodo,
        String raEscolar,
        String curso
) {
}
