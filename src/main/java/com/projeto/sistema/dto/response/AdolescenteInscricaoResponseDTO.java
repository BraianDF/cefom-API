package com.projeto.sistema.dto.response;

import com.projeto.sistema.enums.Genero;
import com.projeto.sistema.enums.Situacao;

import java.time.LocalDate;

public record AdolescenteInscricaoResponseDTO(
        Integer idAdolescente,
        String nome,
        Genero genero,
        LocalDate dataNascimento,
        Integer idade,
        Situacao situacao
) {
}
