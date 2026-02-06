package com.projeto.cefom.dto.request;

import com.projeto.cefom.enums.TipoContratacao;
import com.projeto.cefom.enums.TipoEmpresa;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EmpresaRequestDTO(
        LocalDate dataModificacao,
        @NotBlank(message = "Documento é obrigatório.")
        String documento, //cpf ou cnpj
        Integer numConvenio,
        @NotBlank(message = "Razão social é obrigatório.")
        String razaoSocial,
        String nomeFantasia,
        String apelido,
        @NotNull(message = "Tipo de empresa é obrigatório.")
        TipoEmpresa tipoEmpresa,
        @NotNull(message = "Contratação padrão é obrigatório.")
        TipoContratacao contratacaoPadrao,
        @NotNull(message = "Taxa administrativa é obrigatório.")
        BigDecimal taxaAdministrativa,
        String email,
        @Valid
        TelefonesEmpresaRequestDTO telefones,
        @Valid
        @NotNull(message = "Endereço é obrigatório.")
        EnderecoRequestDTO endereco,
        @Valid
        @NotNull(message = "Responsáveis pela empresa são obrigatórios.")
        ResponsavelEmpresaRequestDTO responsaveis
) {
}
