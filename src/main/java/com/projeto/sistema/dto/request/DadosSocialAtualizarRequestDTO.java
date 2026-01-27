package com.projeto.sistema.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record DadosSocialAtualizarRequestDTO(
        LocalDate dataModificacao,
        @NotNull(message = "Dados social são obrigatórios.")
        DadosSocialRequestDTO dadosSocial
) {
}
