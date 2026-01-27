package com.projeto.sistema.dto.request;

import com.projeto.sistema.enums.Estado;
import com.projeto.sistema.enums.EstadoCivil;
import com.projeto.sistema.enums.Genero;
import com.projeto.sistema.enums.Naturalidade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AdolescenteAtualizarRequestDTO(
        LocalDate dataModificacao,
        @Valid
        @NotNull(message = "Adolescente é obrigatório.")
        AdolescenteMatriculaRequestDTO adolescente
) {
}
