package com.projeto.cefom.dto.response;

import java.time.LocalDate;

public record DadosSocialListarResponseDTO(
        Integer idDadosSocial,
        LocalDate dataInicio,
        LocalDate dataFim,
        Boolean encaminhamento,
        Boolean beneficio
) {
}
