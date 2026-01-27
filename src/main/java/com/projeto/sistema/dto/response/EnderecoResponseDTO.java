package com.projeto.sistema.dto.response;

import com.projeto.sistema.enums.Estado;

import java.time.LocalDate;

public record EnderecoResponseDTO(
        Integer idEndereco,
        String cep,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        Estado estado,
        String territorio,
        LocalDate dataInicio,
        LocalDate dataFim
) {
}
