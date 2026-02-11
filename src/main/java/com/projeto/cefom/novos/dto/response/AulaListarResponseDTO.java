package com.projeto.cefom.novos.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

public record AulaListarResponseDTO(
        Integer idAula,
        Boolean chamadaRealizada,
        LocalDate dataAula,
        LocalTime horarioInicio,
        LocalTime horarioFim,
        String professor,
        String disciplina
) {
}
