package com.projeto.sistema.model;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDate;

@MappedSuperclass
public abstract class Vigencia {
    @Column(name = "dataInicio", nullable = false)
    private LocalDate dataInicio;
    @Column(name = "dataFim")
    private LocalDate dataFim;
    @ManyToOne
    @JoinColumn(name = "idResponsavelInicio")
    private Usuario responsavelInicio;
    @ManyToOne
    @JoinColumn(name = "idResponsavelFim")
    private Usuario responsavelFim;

    public Vigencia() {
    }

    public Vigencia(LocalDate dataInicio, LocalDate dataFim, Usuario responsavelInicio, Usuario responsavelFim) {
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.responsavelInicio = responsavelInicio;
        this.responsavelFim = responsavelFim;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public boolean estaValidoEm(LocalDate data) {
        return dataInicio != null &&
                !dataInicio.isAfter(data) &&
                (dataFim == null || !dataFim.isBefore(data));
    }

    public Usuario getResponsavelInicio() {
        return responsavelInicio;
    }

    public void setResponsavelInicio(Usuario responsavelInicio) {
        this.responsavelInicio = responsavelInicio;
    }

    public Usuario getResponsavelFim() {
        return responsavelFim;
    }

    public void setResponsavelFim(Usuario responsavelFim) {
        this.responsavelFim = responsavelFim;
    }
}
