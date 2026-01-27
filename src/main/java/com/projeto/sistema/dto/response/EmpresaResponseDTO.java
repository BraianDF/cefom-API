package com.projeto.sistema.dto.response;

import com.projeto.sistema.enums.TipoContratacao;
import com.projeto.sistema.enums.TipoEmpresa;

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
