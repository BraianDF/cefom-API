package com.projeto.cefom.dto.response;

import java.time.LocalDate;

public record ResponsavelEmpresaListarResponseDTO(
        Integer idResponsavelEmpresa,
        LocalDate dataInicio,
        LocalDate dataFim,
        String nome
) {
}
