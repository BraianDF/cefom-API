package com.projeto.sistema.dto.response;

import com.projeto.sistema.enums.ModalidadeCurso;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record CursoResponseDTO (
        Integer idCurso,
        String nomeCurso,
        String nomePrograma,
        ModalidadeCurso modalidadeCurso,
        String protocoloAprovacao,
        BigDecimal cargaHorariaTeoricaBasica,
        BigDecimal cargaHorariaTeoricaEspecifica,
        BigDecimal cargaHorariaTeoricaInicial,
        BigDecimal cargaHorarioTeorica,
        BigDecimal cargaHorariaPratica,
        LocalDate dataInicio,
        LocalDate dataFim,
        List<CargoResponseDTO> cargos
) {
}
