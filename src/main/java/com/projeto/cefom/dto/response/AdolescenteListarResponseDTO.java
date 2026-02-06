package com.projeto.cefom.dto.response;

import com.projeto.cefom.enums.Situacao;

public record AdolescenteListarResponseDTO(
        Integer idAdolescente,
        String nome,
        String cpf,
        Situacao situacao
) {
}
