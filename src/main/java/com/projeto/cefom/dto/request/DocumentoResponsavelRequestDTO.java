package com.projeto.cefom.dto.request;

import org.hibernate.validator.constraints.br.CPF;

public record DocumentoResponsavelRequestDTO(
        @CPF
        String cpf,
        String rg,
        String nis
) {
}
