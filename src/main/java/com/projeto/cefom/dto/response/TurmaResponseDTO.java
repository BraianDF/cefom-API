package com.projeto.cefom.dto.response;

import com.projeto.cefom.enums.Situacao;

import java.util.List;

public record TurmaResponseDTO(
        Integer idTurma,
        String nome,
        Situacao tipo,
        List<AlunoParticipacaoResponseDTO> alunos
) {
}
