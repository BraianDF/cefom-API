package com.projeto.cefom.dto.response;

import java.time.LocalDate;

public record AlunoParticipacaoResponseDTO(
        Integer idParticipacao,
        LocalDate dataInicio,
        LocalDate dataFim,
        AlunoMatriculaResponseDTO aluno
) {
}
