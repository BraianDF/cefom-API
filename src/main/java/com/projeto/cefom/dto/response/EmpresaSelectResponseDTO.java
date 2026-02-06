package com.projeto.cefom.dto.response;

import com.projeto.cefom.enums.TipoContratacao;

public record EmpresaSelectResponseDTO(
        Integer idEmpresa,
        String empresa,
        String ResponsavelEntevistas,
        TipoContratacao contratacaoPadrao
) {
}
