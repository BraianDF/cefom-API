package com.projeto.sistema.dto.response;

import com.projeto.sistema.enums.Situacao;

public record AdolescenteListarResponseDTO(
        Integer idAdolescente,
        String nome,
        String cpf,
        Situacao situacao
) {
}
