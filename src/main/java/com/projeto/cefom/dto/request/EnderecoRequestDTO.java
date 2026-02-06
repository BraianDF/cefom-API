package com.projeto.cefom.dto.request;

import com.projeto.cefom.enums.Estado;

public record EnderecoRequestDTO(
        String cep,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        Estado estado
) {
}
