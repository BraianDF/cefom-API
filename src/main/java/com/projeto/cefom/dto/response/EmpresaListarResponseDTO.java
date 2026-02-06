package com.projeto.cefom.dto.response;

import com.projeto.cefom.enums.TipoEmpresa;

public record EmpresaListarResponseDTO(
        Integer idEmpresa,
        String empresa,
        TipoEmpresa tipoEmpresa
) {
}
