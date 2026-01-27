package com.projeto.sistema.dto.response;

import java.time.LocalDate;

public record EntrevistaListarResponseDTO (
    Integer idEntrevista,
    LocalDate dataEntrevista,
    EmpresaListarResponseDTO empresa
) {

}