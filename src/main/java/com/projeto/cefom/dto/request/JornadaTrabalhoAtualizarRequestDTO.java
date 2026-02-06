package com.projeto.cefom.dto.request;

import com.projeto.cefom.enums.DiaSemana;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record JornadaTrabalhoAtualizarRequestDTO(
        LocalDate dataModificacao,
        @NotNull(message = "Dia do curso é obrigatório.")
        BigDecimal cargaHoraria,
        @NotNull(message = "Horário de entrada durante a semana é obrigatório.")
        LocalTime horarioSemanaEntrada,
        @NotNull(message = "Horário de saída durante a semana é obrigatório.")
        LocalTime horarioSemanaSaida,
        LocalTime horarioAlmocoEntrada,
        LocalTime horarioAlmocoSaida,
        @NotNull(message = "Horário de entrada durante o sábado é obrigatório.")
        LocalTime horarioSabadoEntrada,
        @NotNull(message = "Horário de saída durante o sábado é obrigatório.")
        LocalTime horarioSabadoSaida,
        @NotNull(message = "Dia do curso é obrigatório.")
        DiaSemana diaCurso,
        DiaSemana diaFolga
) {
}
