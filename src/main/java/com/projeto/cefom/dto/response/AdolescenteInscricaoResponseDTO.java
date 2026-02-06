package com.projeto.cefom.dto.response;

import com.projeto.cefom.enums.Genero;
import com.projeto.cefom.enums.Situacao;

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
