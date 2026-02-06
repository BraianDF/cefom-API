package com.projeto.cefom.dto.response;

public record InscricaoCriarResponseDTO(
        InscricaoResponseDTO inscricao,
        DocumentoInscricaoResponseDTO documento,
        AdolescenteInscricaoResponseDTO adolescente,
        EnderecoResponseDTO endereco,
        EscolaridadeResponseDTO escolaridade,
        TelefonesResponseDTO telefones
        ) {
}
