package com.projeto.cefom.dto.request;

import com.projeto.cefom.enums.ModalidadeCurso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record CursoRequestDTO (
        @NotNull(message = "Data de inicio é obrigatório.")
        LocalDate dataInicio,
        @NotNull(message = "Data de fim é obrigatório.")
        LocalDate dataFim,
        @NotBlank(message = "Nome do curso é obrigatório.")
        String nomeCurso,
        @NotBlank(message = "Nome do programa é obrigatório.")
        String nomePrograma,
        @NotNull(message = "Modalidade do curso é obrigatório.")
        ModalidadeCurso modalidadeCurso,
        @NotBlank(message = "Protocolo de aprovação é obrigatório.")
        String protocoloAprovacao,
        @NotNull(message = "Carga horária teórica básica é obrigatório.")
        BigDecimal cargaHorariaTeoricaBasica,
        @NotNull(message = "Carga horária teórica específica é obrigatório.")
        BigDecimal cargaHorariaTeoricaEspecifica,
        @NotNull(message = "Carga horária teórica inicial é obrigatório.")
        BigDecimal cargaHorariaTeoricaInicial,
        @NotNull(message = "Carga horária prática é obrigatório.")
        BigDecimal cargaHorariaPratica,
        @NotNull(message = "Cargos são obrigatórios.")
        List<CargoCursoResquestDTO> cargos
) {
}
