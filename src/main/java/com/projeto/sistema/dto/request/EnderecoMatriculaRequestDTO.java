package com.projeto.sistema.dto.request;

import com.projeto.sistema.enums.Estado;
import com.projeto.sistema.enums.TipoResidencia;

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
