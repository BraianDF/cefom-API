package com.projeto.cefom.dto.response;

import com.projeto.cefom.enums.*;

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
