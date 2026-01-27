package com.projeto.sistema.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record VinculoEntrevistaMatriculaRequestDTO(
        @NotNull(message = "Matricula é obrigatório.")
        Integer idMatricula,
        @NotNull(message = "Horário da entrevista é obrigatório.")
        LocalTime horarioEntrevista,
        String situacao
) {
}
