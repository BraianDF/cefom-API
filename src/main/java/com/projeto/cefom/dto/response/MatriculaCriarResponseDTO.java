package com.projeto.cefom.dto.response;

public record MatriculaCriarResponseDTO(
        MatriculaResponseDTO matricula,
        AdolescenteMatriculaResponseDTO adolescente,
        EnderecoMatriculaResponseDTO endereco,
        TelefonesResponseDTO telefones,
        EmailResponseDTO emailAdolescente,
        DocumentoAdolescenteResponseDTO documento,
        CaracteristicaResponseDTO caracteristica,
        EscolaridadeResponseDTO escolaridade,
        DadosSocialResponseDTO dadosSocial,
        FamiliaresResponseDTO familiares
) {
}
