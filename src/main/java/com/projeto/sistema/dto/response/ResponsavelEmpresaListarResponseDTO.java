package com.projeto.sistema.dto.response;

import java.time.LocalDate;

public record ResponsavelEmpresaListarResponseDTO(
        Integer idResponsavelEmpresa,
        LocalDate dataInicio,
        LocalDate dataFim,
        String nome
) {
}
