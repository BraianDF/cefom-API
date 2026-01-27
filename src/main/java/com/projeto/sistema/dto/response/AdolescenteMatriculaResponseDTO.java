package com.projeto.sistema.dto.response;

import com.projeto.sistema.enums.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AdolescenteMatriculaResponseDTO(
        Integer idAdolescente,
        String nome,
        Genero genero,
        LocalDate dataNascimento,
        String cidadeNascimento,
        Estado estadoNascimento,
        String paisNascimento,
        Naturalidade naturalidade,
        EstadoCivil estadoCivil,
        String mae,
        String pai,
        String conjuge,
        Integer idade,
        Situacao situacao
) {
}
