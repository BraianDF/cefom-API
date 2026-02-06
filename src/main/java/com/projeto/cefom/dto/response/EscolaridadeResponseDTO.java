package com.projeto.cefom.dto.response;

import com.projeto.cefom.enums.Periodo;
import com.projeto.cefom.enums.Serie;

import java.time.LocalDate;

public record EscolaridadeResponseDTO(
        Integer idEscolaridade,
        EscolaListarResponseDTO escola,
        Serie serie,
        Periodo periodo,
        String raEscolar,
        String curso,
        LocalDate dataInicio,
        LocalDate dataFim

) {
}
