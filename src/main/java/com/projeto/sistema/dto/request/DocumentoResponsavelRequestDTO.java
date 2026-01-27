package com.projeto.sistema.dto.request;

import org.hibernate.validator.constraints.br.CPF;

public record DocumentoResponsavelRequestDTO(
        @CPF
        String cpf,
        String rg,
        String nis
) {
}
