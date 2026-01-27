package com.projeto.sistema.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record DadosSocialAtualizarRequestDTO(
        LocalDate dataModificacao,
        @Valid
        @NotNull(message = "Dados social são obrigatórios.")
        DadosSocialRequestDTO dadosSocial
) {
}
