package com.projeto.sistema.dto.request;

import com.projeto.sistema.enums.TipoContratacao;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ContratoAtualizarRequestDTO(
        LocalDate dataModificacao,
        @NotNull(message = "Data de início de contrato é obrigatório.")
        LocalDate DataInicio,
        @NotNull(message = "Data de término de contrato é obrigatório.")
        LocalDate dataTermino,
        @NotNull(message = "Tipo de contratação é obrigatório.")
        TipoContratacao tipoContratacao,
        @NotNull(message = "Cargo é obrigatório.")
        Integer idCargo
) {
}
