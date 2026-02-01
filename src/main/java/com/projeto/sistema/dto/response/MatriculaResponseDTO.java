package com.projeto.sistema.dto.response;

import com.projeto.sistema.enums.DesligamentoMatricula;
import com.projeto.sistema.enums.SituacaoMatricula;
import com.projeto.sistema.image.UploadFotoAdolescenteResponseDTO;

import java.time.LocalDate;

public record MatriculaResponseDTO(
        Integer idMatricula,
        Integer numMatricula,
        SituacaoMatricula situacaoMatricula,
        LocalDate dataMatricula,
        LocalDate dataDesligamento,
        DesligamentoMatricula motivoDesligamento,
        UploadFotoAdolescenteResponseDTO foto
) {
}
