package com.projeto.cefom.dto.request;

import com.projeto.cefom.enums.Estado;
import com.projeto.cefom.enums.TipoResidencia;

public record EnderecoMatriculaRequestDTO(
        String cep,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        Estado estado,
        TipoResidencia tipoResidencia
) {
}
