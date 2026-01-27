package com.projeto.sistema.dto.response;

import com.projeto.sistema.enums.TipoResponsabilidade;

import java.time.LocalDate;

public record ResponsavelEmpresaResponseDTO(
        Integer idResponsavelEmpresa,
        String nome,
        TipoResponsabilidade responsabilidade,
        LocalDate dataInicio,
        LocalDate dataFim
) {
}
