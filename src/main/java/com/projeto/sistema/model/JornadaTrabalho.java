package com.projeto.sistema.model;

import com.projeto.sistema.enums.DiaSemana;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "jornadasTrabalho")
public class JornadaTrabalho extends Vigencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idJornadaTrabalho;
    @Column(name = "cargaHoraria", precision = 5, scale = 2)
    private BigDecimal cargaHoraria;
    @Column(name = "horarioSemanaEntrada")
    private LocalTime horarioSemanaEntrada;
    @Column(name = "horarioSemanaSaida")
    private LocalTime horarioSemanaSaida;
    @Column(name = "horarioAlmocoEntrada")
    private LocalTime horarioAlmocoEntrada;
    @Column(name = "horarioAlmocoSaida")
    private LocalTime horarioAlmocoSaida;
    @Column(name = "horarioSabadoEntrada")
    private LocalTime horarioSabadoEntrada;
    @Column(name = "horarioSabadoSaida")
    private LocalTime horarioSabadoSaida;
    @Enumerated(EnumType.STRING)
    @Column(name = "diaCurso")
    private DiaSemana diaCurso;
    @Enumerated(EnumType.STRING)
    @Column(name = "diaFolga")
    private DiaSemana diaFolga;
    @ManyToOne
    @JoinColumn(name = "idContrato")
    private Contrato contrato;

    public JornadaTrabalho() {
    }

    public JornadaTrabalho(LocalDate dataInicio, LocalDate dataFim, Usuario responsavelInicio, Usuario responsavelFim, Integer idJornadaTrabalho, BigDecimal cargaHoraria, LocalTime horarioSemanaEntrada, LocalTime horarioSemanaSaida, LocalTime horarioAlmocoEntrada, LocalTime horarioAlmocoSaida, LocalTime horarioSabadoEntrada, LocalTime horarioSabadoSaida, DiaSemana diaCurso, DiaSemana diaFolga, Contrato contrato) {
        super(dataInicio, dataFim, responsavelInicio, responsavelFim);
        this.idJornadaTrabalho = idJornadaTrabalho;
        this.cargaHoraria = cargaHoraria;
        this.horarioSemanaEntrada = horarioSemanaEntrada;
        this.horarioSemanaSaida = horarioSemanaSaida;
        this.horarioAlmocoEntrada = horarioAlmocoEntrada;
        this.horarioAlmocoSaida = horarioAlmocoSaida;
        this.horarioSabadoEntrada = horarioSabadoEntrada;
        this.horarioSabadoSaida = horarioSabadoSaida;
        this.diaCurso = diaCurso;
        this.diaFolga = diaFolga;
        this.contrato = contrato;
    }

    public Integer getIdJornadaTrabalho() {
        return idJornadaTrabalho;
    }

    public void setIdJornadaTrabalho(Integer idJornadaTrabalho) {
        this.idJornadaTrabalho = idJornadaTrabalho;
    }

    public BigDecimal getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(BigDecimal cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public LocalTime getHorarioSemanaEntrada() {
        return horarioSemanaEntrada;
    }

    public void setHorarioSemanaEntrada(LocalTime horarioSemanaEntrada) {
        this.horarioSemanaEntrada = horarioSemanaEntrada;
    }

    public LocalTime getHorarioSemanaSaida() {
        return horarioSemanaSaida;
    }

    public void setHorarioSemanaSaida(LocalTime horarioSemanaSaida) {
        this.horarioSemanaSaida = horarioSemanaSaida;
    }

    public LocalTime getHorarioAlmocoEntrada() {
        return horarioAlmocoEntrada;
    }

    public void setHorarioAlmocoEntrada(LocalTime horarioAlmocoEntrada) {
        this.horarioAlmocoEntrada = horarioAlmocoEntrada;
    }

    public LocalTime getHorarioAlmocoSaida() {
        return horarioAlmocoSaida;
    }

    public void setHorarioAlmocoSaida(LocalTime horarioAlmocoSaida) {
        this.horarioAlmocoSaida = horarioAlmocoSaida;
    }

    public LocalTime getHorarioSabadoEntrada() {
        return horarioSabadoEntrada;
    }

    public void setHorarioSabadoEntrada(LocalTime horarioSabadoEntrada) {
        this.horarioSabadoEntrada = horarioSabadoEntrada;
    }

    public LocalTime getHorarioSabadoSaida() {
        return horarioSabadoSaida;
    }

    public void setHorarioSabadoSaida(LocalTime horarioSabadoSaida) {
        this.horarioSabadoSaida = horarioSabadoSaida;
    }

    public DiaSemana getDiaCurso() {
        return diaCurso;
    }

    public void setDiaCurso(DiaSemana diaCurso) {
        this.diaCurso = diaCurso;
    }

    public DiaSemana getDiaFolga() {
        return diaFolga;
    }

    public void setDiaFolga(DiaSemana diaFolga) {
        this.diaFolga = diaFolga;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }
}
