package com.projeto.sistema.dto.response;

import com.projeto.sistema.dto.response.EscolaListarResponseDTO;
import com.projeto.sistema.enums.Periodo;
import com.projeto.sistema.enums.Serie;

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
