package com.projeto.cefom.dto.response;

import com.projeto.cefom.enums.TipoContratacao;
import com.projeto.cefom.enums.TipoEmpresa;

public record EmpresaResponseDTO (
        Integer idEmpresa,
        Integer numConvenio,
        String razaoSocial,
        String nomeFantasia,
        String apelido,
        TipoEmpresa tipoEmpresa,
        TipoContratacao contratacaoPadrao
) {
}
