package com.projeto.cefom.novos.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

public record AulaResponseDTO(
        Integer idAula,
        Boolean chamadaRealizada,
        LocalDate dataAula,
        LocalTime horarioInicio,
        LocalTime horarioFim,
        ProfessorResponseDTO professor,
        DisciplinaResponseDTO disciplina,
        SalaAulaResponseDTO salaAula,
        TurmaResponseDTO turma
) {
}
