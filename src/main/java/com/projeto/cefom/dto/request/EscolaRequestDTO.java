package com.projeto.cefom.dto.request;

import com.projeto.cefom.enums.TipoEscola;
import jakarta.validation.Valid;
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
        @Valid
        @NotNull(message = "Endereço da escola é obrigatório.")
        EnderecoRequestDTO endereco
) {
}
