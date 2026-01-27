package com.projeto.sistema.dto.response;

import java.time.LocalDate;

public record DadosSocialListarResponseDTO(
        Integer idDadosSocial,
        LocalDate dataInicio,
        LocalDate dataFim,
        Boolean encaminhamento,
        Boolean beneficio
) {
}
