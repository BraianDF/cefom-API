package com.projeto.sistema.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EntrevistaRequestDTO(
        @NotNull(message = "Empresa é obrigatório.")
        Integer idEmpresa,
        @NotBlank(message = "Responsável pela entrevista é obrigatório.")
        String responsavelEntevistas,
        @NotNull(message = "Data da entrevista é obrigatório.")
        LocalDate dataEntrevista
) {
}
