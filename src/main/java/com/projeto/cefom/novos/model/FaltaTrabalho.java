package com.projeto.cefom.novos.model;

import com.projeto.cefom.model.Contrato;
import com.projeto.cefom.model.Usuario;
import com.projeto.cefom.model.Vigencia;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "faltasTrabalhos")
public class FaltaTrabalho extends Vigencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idFaltaTrabalho;
    @ManyToOne
    @JoinColumn(name = "idContrato")
    private Contrato contrato;
    @ManyToOne
    @JoinColumn(name = "idJustificativa")
    private JustificativaFalta justificativa;
    @Transient
    private Boolean justificada;

    public FaltaTrabalho() {
    }

    public FaltaTrabalho(LocalDate dataInicio, LocalDate dataFim, Usuario responsavelInicio, Usuario responsavelFim, Integer idFaltaTrabalho, Contrato contrato, JustificativaFalta justificativa, Boolean justificada) {
        super(dataInicio, dataFim, responsavelInicio, responsavelFim);
        this.idFaltaTrabalho = idFaltaTrabalho;
        this.contrato = contrato;
        this.justificativa = justificativa;
        this.justificada = justificada;
    }

    public Integer getIdFaltaTrabalho() {
        return idFaltaTrabalho;
    }

    public void setIdFaltaTrabalho(Integer idFaltaTrabalho) {
        this.idFaltaTrabalho = idFaltaTrabalho;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public JustificativaFalta getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(JustificativaFalta justificativa) {
        this.justificativa = justificativa;
    }

    public Boolean getJustificada() {
        return justificada;
    }

    public void setJustificada(Boolean justificada) {
        this.justificada = justificada;
    }
}
