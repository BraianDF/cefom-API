package com.projeto.cefom.dto.response;

import com.projeto.cefom.enums.DesligamentoMatricula;
import com.projeto.cefom.enums.SituacaoMatricula;
import com.projeto.cefom.image.dto.UploadFotoAdolescenteResponseDTO;

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
