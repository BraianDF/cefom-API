package com.projeto.sistema.dto.response;

import com.projeto.sistema.image.UploadFotoAdolescenteResponseDTO;

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
