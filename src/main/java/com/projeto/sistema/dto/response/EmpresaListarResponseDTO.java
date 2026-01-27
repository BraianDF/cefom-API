package com.projeto.sistema.dto.response;

import com.projeto.sistema.enums.TipoEmpresa;

public record EmpresaListarResponseDTO(
        Integer idEmpresa,
        String empresa,
        TipoEmpresa tipoEmpresa
) {
}
