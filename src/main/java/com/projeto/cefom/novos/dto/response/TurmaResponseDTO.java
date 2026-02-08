package com.projeto.cefom.novos.dto.response;

import com.projeto.cefom.enums.Situacao;

import java.util.List;

public record TurmaResponseDTO(
        Integer idTurma,
        String nome,
        Situacao tipo,
        List<AlunoParticipacaoResponseDTO> alunos
) {
}
