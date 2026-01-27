package com.projeto.sistema.dto.response;

public record EmpresaCriarResponseDTO(
        EmpresaResponseDTO empresa,
        DocumentoEmpresaResponseDTO documento, //cpf ou cnpj
        TaxaAdministrativaResponseDTO taxaAdministrativa,
        EmailResponseDTO email,
        TelefonesEmpresaResponseDTO telefones,
        EnderecoResponseDTO endereco,
        ResponsaveisEmpresaResponseDTO responsaveis
) {
}
