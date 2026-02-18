package com.projeto.cefom.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record AulaRequestDTO(
        @NotNull(message = "Data é obrigatório.")
        LocalDate dataAula,
        @NotNull(message = "Horário de Inicio é obrigatório.")
        LocalTime horarioInicio,
        @NotNull(message = "Horário de Fim é obrigatório.")
        LocalTime horarioFim,
        @NotNull(message = "Professor é obrigatório.")
        Integer idProfessor,
        @NotNull(message = "Disciplina é obrigatório.")
        Integer idDisciplina,
        @NotNull(message = "Sala de Aula é obrigatório.")
        Integer idSalaAula,
        @NotNull(message = "Turma é obrigatório.")
        Integer idTurma
) {
}
