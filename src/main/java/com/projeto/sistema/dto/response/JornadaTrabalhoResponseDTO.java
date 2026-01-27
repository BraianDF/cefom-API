package com.projeto.sistema.dto.response;

import com.projeto.sistema.enums.DiaSemana;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record JornadaTrabalhoResponseDTO(
        Integer idJornadaTrabalho,
        BigDecimal cargaHoraria,
        LocalTime horarioSemanaEntrada,
        LocalTime horarioSemanaSaida,
        LocalTime horarioAlmocoEntrada,
        LocalTime horarioAlmocoSaida,
        LocalTime horarioSabadoEntrada,
        LocalTime horarioSabadoSaida,
        DiaSemana diaCurso,
        DiaSemana diaFolga,
        LocalDate dataInicio,
        LocalDate dataFim
) {
}
