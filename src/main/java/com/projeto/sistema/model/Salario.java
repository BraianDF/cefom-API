package com.projeto.sistema.model;

import com.projeto.sistema.enums.TipoSalario;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "salarios")
public class Salario extends Vigencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idSalario;
    @Column(name = "valorBase", precision = 7, scale = 2)
    private BigDecimal valorBase;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipoSalario")
    private TipoSalario tipoSalario;
    @ManyToOne
    @JoinColumn(name = "idContrato")
    private Contrato contrato;

    public Salario() {
    }

    public Salario(LocalDate dataInicio, LocalDate dataFim, Usuario responsavelInicio, Usuario responsavelFim, Integer idSalario, BigDecimal valorBase, TipoSalario tipoSalario, Contrato contrato) {
        super(dataInicio, dataFim, responsavelInicio, responsavelFim);
        this.idSalario = idSalario;
        this.valorBase = valorBase;
        this.tipoSalario = tipoSalario;
        this.contrato = contrato;
    }

    public Integer getIdSalario() {
        return idSalario;
    }

    public void setIdSalario(Integer idSalario) {
        this.idSalario = idSalario;
    }

    public BigDecimal getValorBase() {
        return valorBase;
    }

    public void setValorBase(BigDecimal valorBase) {
        this.valorBase = valorBase;
    }

    public TipoSalario getTipoSalario() {
        return tipoSalario;
    }

    public void setTipoSalario(TipoSalario tipoSalario) {
        this.tipoSalario = tipoSalario;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }
}
