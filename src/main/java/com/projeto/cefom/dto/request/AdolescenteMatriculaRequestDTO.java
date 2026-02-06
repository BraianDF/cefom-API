package com.projeto.cefom.dto.request;

import com.projeto.cefom.enums.Estado;
import com.projeto.cefom.enums.EstadoCivil;
import com.projeto.cefom.enums.Genero;
import com.projeto.cefom.enums.Naturalidade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AdolescenteMatriculaRequestDTO(
        @NotBlank(message = "Nome é obrigatório.")
        String nome,
        @NotNull(message = "Gênero é obrigatório.")
        Genero genero,
        @NotNull(message = "Data de nascimento é obrigatório.")
        LocalDate dataNascimento,
        String cidadeNascimento,
        Estado estadoNascimento,
        String paisNascimento,
        Naturalidade naturalidade,
        EstadoCivil estadoCivil,
        String mae,
        String pai,
        String conjuge
) {
}
