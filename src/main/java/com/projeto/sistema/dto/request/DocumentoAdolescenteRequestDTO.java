package com.projeto.sistema.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record DocumentoAdolescenteRequestDTO(
        @NotBlank(message = "CPF é obrigatório.")
        @CPF
        String cpf,
        String ctps,
        String rg,
        LocalDate dataEmissaoRG,
        String nis,
        String sus,
        String tituloEleitor,
        String zonaTE,
        String secaoTE,
        String cnh,
        String categoriaCNH,
        String reservista
) {
}
