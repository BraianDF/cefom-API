package com.projeto.sistema.dto.response;

import com.projeto.sistema.image.UploadFotoAdolescenteResponseDTO;

public record AdolescenteResponseDTO (
        AdolescenteMatriculaResponseDTO adolescente,
        DocumentoAdolescenteResponseDTO documento,
        EnderecoMatriculaResponseDTO endereco,
        EscolaridadeResponseDTO escolaridade,
        TelefonesResponseDTO telefones,
        EmailResponseDTO emailAdolescente,
        CaracteristicaResponseDTO caracteristica,
        DadosSocialResponseDTO dadosSocial,
        FamiliaresResponseDTO familiares,
        InscricaoResponseDTO inscricao,
        MatriculaResponseDTO matricula,
        UploadFotoAdolescenteResponseDTO foto
) {
}
