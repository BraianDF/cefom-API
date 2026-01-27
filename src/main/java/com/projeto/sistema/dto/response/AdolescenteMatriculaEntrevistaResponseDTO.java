package com.projeto.sistema.dto.response;

import com.projeto.sistema.enums.Situacao;

import java.time.LocalDate;

public record AdolescenteMatriculaEntrevistaResponseDTO(
        Integer idAdolescente,
        Integer idMatricula,
        Integer numMatricula,
        String nome,
        LocalDate dataNascimento,
        Integer idade,
        Situacao situacao
) {
}
