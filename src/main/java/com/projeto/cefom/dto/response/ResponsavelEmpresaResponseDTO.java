package com.projeto.cefom.dto.response;

import com.projeto.cefom.enums.TipoResponsabilidade;

import java.time.LocalDate;

public record ResponsavelEmpresaResponseDTO(
        Integer idResponsavelEmpresa,
        String nome,
        TipoResponsabilidade responsabilidade,
        LocalDate dataInicio,
        LocalDate dataFim
) {
}
