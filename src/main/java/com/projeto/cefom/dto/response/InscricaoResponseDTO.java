package com.projeto.cefom.dto.response;

import com.projeto.cefom.enums.FinalizacaoInscricao;
import com.projeto.cefom.image.UploadFotoAdolescenteResponseDTO;

import java.time.LocalDate;

public record InscricaoResponseDTO(
        Integer idInscricao,
        Integer numInscricao,
        String observacao,
        LocalDate dataInscricao,
        LocalDate dataFinalizacao,
        FinalizacaoInscricao motivoFinalizacao,
        UploadFotoAdolescenteResponseDTO foto
) {
}
