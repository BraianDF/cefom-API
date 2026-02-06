package com.projeto.cefom.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "taxasAdministrativas")
public class TaxaAdministrativa extends Vigencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idTaxaAdministrativa;
    @Column(name = "valorTaxa", precision = 5, scale = 2)
    private BigDecimal valorTaxa;
    @ManyToOne
    @JoinColumn(name = "idEmpresa")
    private Empresa empresa;

    public TaxaAdministrativa() {
    }

    public TaxaAdministrativa(LocalDate dataInicio, LocalDate dataFim, Usuario responsavelInicio, Usuario responsavelFim, Integer idTaxaAdministrativa, BigDecimal valorTaxa, Empresa empresa) {
        super(dataInicio, dataFim, responsavelInicio, responsavelFim);
        this.idTaxaAdministrativa = idTaxaAdministrativa;
        this.valorTaxa = valorTaxa;
        this.empresa = empresa;
    }

    public Integer getIdTaxaAdministrativa() {
        return idTaxaAdministrativa;
    }

    public void setIdTaxaAdministrativa(Integer idTaxaAdministrativa) {
        this.idTaxaAdministrativa = idTaxaAdministrativa;
    }

    public BigDecimal getValorTaxa() {
        return valorTaxa;
    }

    public void setValorTaxa(BigDecimal valorTaxa) {
        this.valorTaxa = valorTaxa;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}
