package com.projeto.sistema.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EnderecoAdolescenteAtualizarRequestDTO (
        LocalDate dataModificacao,
        @NotNull(message = "Endereço é obrigatório.")
        EnderecoMatriculaRequestDTO endereco
) {
}
