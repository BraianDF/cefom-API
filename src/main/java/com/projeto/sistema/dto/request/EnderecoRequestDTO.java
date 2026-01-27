package com.projeto.sistema.dto.request;

import com.projeto.sistema.enums.Estado;

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
