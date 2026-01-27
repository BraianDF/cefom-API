package com.projeto.sistema.dto.request;

import com.projeto.sistema.enums.Genero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AdolescenteInscricaoRequestDTO(
        @NotBlank(message = "Nome é obrigatório.")
        String nome,
        @NotNull(message = "Gênero é obrigatório.")
        Genero genero,
        @NotNull(message = "Data de nascimento é obrigatório.")
        LocalDate dataNascimento
) {
}
