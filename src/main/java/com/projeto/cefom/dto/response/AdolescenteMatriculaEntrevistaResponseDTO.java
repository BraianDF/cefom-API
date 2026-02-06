package com.projeto.cefom.dto.response;

import com.projeto.cefom.enums.Situacao;

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
