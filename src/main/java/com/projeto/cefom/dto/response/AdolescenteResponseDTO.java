package com.projeto.cefom.dto.response;

import com.projeto.cefom.image.dto.UploadFotoAdolescenteResponseDTO;

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
