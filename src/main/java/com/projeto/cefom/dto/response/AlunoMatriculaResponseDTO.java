package com.projeto.cefom.dto.response;

import com.projeto.cefom.enums.Situacao;

public record AlunoMatriculaResponseDTO(
        Integer idAdolescente,
        Integer idMatricula,
        Integer numMatricula,
        String nome,
        Integer idade,
        Situacao situacao
) {
}
