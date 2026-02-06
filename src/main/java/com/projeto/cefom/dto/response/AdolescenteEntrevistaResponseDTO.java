package com.projeto.cefom.dto.response;

public record AdolescenteEntrevistaResponseDTO(
        VinculoEntrevistaMatriculaResponseDTO entrevista,
        AdolescenteMatriculaEntrevistaResponseDTO adolescente,
        DocumentoAdolescenteEntrevistaResponseDTO documento,
        EnderecoResponseDTO endereco,
        EscolaridadeResponseDTO escolaridade
) {
}
