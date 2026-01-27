package com.projeto.sistema.dto.response;

import com.projeto.sistema.dto.response.EnderecoResponseDTO;
import com.projeto.sistema.dto.response.EscolaridadeResponseDTO;

public record AdolescenteEntrevistaResponseDTO(
        VinculoEntrevistaMatriculaResponseDTO entrevista,
        AdolescenteMatriculaEntrevistaResponseDTO adolescente,
        DocumentoAdolescenteEntrevistaResponseDTO documento,
        EnderecoResponseDTO endereco,
        EscolaridadeResponseDTO escolaridade
) {
}
