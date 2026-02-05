package com.projeto.sistema.dto.response;

import com.projeto.sistema.enums.TipoContratacao;

public record EmpresaSelectResponseDTO(
        Integer idEmpresa,
        String empresa,
        String ResponsavelEntevistas,
        TipoContratacao contratacaoPadrao
) {
}
