package com.projeto.sistema.dto.response;

import com.projeto.sistema.image.UploadFotoAdolescenteResponseDTO;

public record InscricaoCriarResponseDTO(
        InscricaoResponseDTO inscricao,
        DocumentoInscricaoResponseDTO documento,
        AdolescenteInscricaoResponseDTO adolescente,
        EnderecoResponseDTO endereco,
        EscolaridadeResponseDTO escolaridade,
        TelefonesResponseDTO telefones
        ) {
}
