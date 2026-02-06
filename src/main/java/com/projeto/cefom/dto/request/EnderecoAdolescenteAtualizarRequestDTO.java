package com.projeto.cefom.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EnderecoAdolescenteAtualizarRequestDTO (
        LocalDate dataModificacao,
        @Valid
        @NotNull(message = "Endereço é obrigatório.")
        EnderecoMatriculaRequestDTO endereco
) {
}
