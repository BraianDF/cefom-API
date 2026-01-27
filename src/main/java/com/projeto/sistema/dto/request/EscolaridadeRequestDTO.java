package com.projeto.sistema.dto.request;

import com.projeto.sistema.enums.Periodo;
import com.projeto.sistema.enums.Serie;

public record EscolaridadeRequestDTO(
        Integer idEscola,
        Serie serie,
        Periodo periodo,
        String raEscolar,
        String curso
) {
}
