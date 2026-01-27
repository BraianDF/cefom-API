package com.projeto.sistema.dto.response;

import com.projeto.sistema.enums.TipoEscola;
import jakarta.validation.constraints.NotNull;

public record EscolaResponseDTO (
        Integer idEscola,
        String nome,
        TipoEscola tipo,
        EnderecoResponseDTO endereco
) {
}
