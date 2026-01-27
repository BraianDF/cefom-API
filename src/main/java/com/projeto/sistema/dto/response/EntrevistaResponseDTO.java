package com.projeto.sistema.dto.response;

import java.time.LocalDate;
import java.util.List;

public record EntrevistaResponseDTO (
        Integer idEntrevista,
        LocalDate dataEntrevista,
        Boolean entrevistaCancelada,
        EmpresaEntrevistaResponseDTO empresa,
        List<AdolescenteEntrevistaResponseDTO> adolescentes
) {
}
