package com.projeto.sistema.dto.request;

import com.projeto.sistema.enums.TipoEscola;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EscolaRequestDTO(
        LocalDate dataModificacao,
        @NotBlank(message = "Nome da escola é obrigatório.")
        String nome,
        @NotNull(message = "Tipo de escola é obrigatório.")
        TipoEscola tipo,

        // Endereço
        @NotNull(message = "Endereço da escola é obrigatório.")
        EnderecoRequestDTO endereco
) {
}
