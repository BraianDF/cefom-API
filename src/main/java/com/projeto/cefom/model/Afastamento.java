package com.projeto.cefom.model;

import com.projeto.cefom.enums.MotivoAfastamento;
import com.projeto.cefom.utils.TextoUtils;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "afastamentos")
public class Afastamento extends Vigencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idAfastamento;
    @Column(name = "qtdeDias")
    private Integer qtdeDias;
    @Enumerated(EnumType.STRING)
    @Column(name = "motivoAfastamento")
    private MotivoAfastamento motivoAfastamento;
    @Column(name = "outroMotivoAfastamento", length = 50)
    private String outroMotivoAfastamento;
    @Column(name = "observacao", length = 50)
    private String observacao;
    @ManyToOne
    @JoinColumn(name = "idContrato")
    private Contrato contrato;

    public Afastamento() {
    }

    public Afastamento(LocalDate dataInicio, LocalDate dataFim, Usuario responsavelInicio, Usuario responsavelFim, Integer idAfastamento, Integer qtdeDias, MotivoAfastamento motivoAfastamento, String outroMotivoAfastamento, String observacao, Contrato contrato) {
        super(dataInicio, dataFim, responsavelInicio, responsavelFim);
        this.idAfastamento = idAfastamento;
        this.qtdeDias = qtdeDias;
        this.motivoAfastamento = motivoAfastamento;
        this.outroMotivoAfastamento = outroMotivoAfastamento;
        this.observacao = observacao;
        this.contrato = contrato;
    }

    public Integer getIdAfastamento() {
        return idAfastamento;
    }

    public void setIdAfastamento(Integer idAfastamento) {
        this.idAfastamento = idAfastamento;
    }

    public Integer getQtdeDias() {
        return qtdeDias;
    }

    public void setQtdeDias(Integer qtdeDias) {
        this.qtdeDias = qtdeDias;
    }

    public MotivoAfastamento getMotivoAfastamento() {
        return motivoAfastamento;
    }

    public void setMotivoAfastamento(MotivoAfastamento motivoAfastamento) {
        this.motivoAfastamento = motivoAfastamento;
    }

    public String getOutroMotivoAfastamento() {
        return outroMotivoAfastamento;
    }

    public void setOutroMotivoAfastamento(String outroMotivoAfastamento) {
        this.outroMotivoAfastamento = TextoUtils.normalizar(outroMotivoAfastamento);
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = TextoUtils.normalizar(observacao);
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }
}
