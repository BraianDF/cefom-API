package com.projeto.sistema.dto.response;

import com.projeto.sistema.enums.FinalizacaoInscricao;
import com.projeto.sistema.image.UploadFotoAdolescenteResponseDTO;

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
