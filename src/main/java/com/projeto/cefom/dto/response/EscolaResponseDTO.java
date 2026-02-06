package com.projeto.cefom.dto.response;

import com.projeto.cefom.enums.TipoEscola;

public record EscolaResponseDTO (
        Integer idEscola,
        String nome,
        TipoEscola tipo,
        EnderecoResponseDTO endereco
) {
}
