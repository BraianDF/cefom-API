package com.projeto.cefom.novos.dto.response;

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
