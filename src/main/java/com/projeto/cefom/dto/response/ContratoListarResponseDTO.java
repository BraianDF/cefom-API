package com.projeto.cefom.dto.response;

import java.time.LocalDate;

public record ContratoListarResponseDTO(
        Integer idContrato,
        LocalDate dataInicio,
        LocalDate dataFim,
        EmpresaListarResponseDTO empresa
) {
}
