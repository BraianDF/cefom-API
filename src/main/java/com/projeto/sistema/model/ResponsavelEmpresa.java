package com.projeto.sistema.model;

import com.projeto.sistema.enums.TipoResponsabilidade;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "ResponsaveisEmpresa")
public class ResponsavelEmpresa extends Vigencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idResponsavelEmpresa;
    @Column(name = "nome", length = 50, nullable = false)
    private String nome;
    @Enumerated(EnumType.STRING)
    @Column(name = "responsabilidade")
    private TipoResponsabilidade responsabilidade;
    @ManyToOne
    @JoinColumn(name = "idEmpresa")
    private Empresa empresa;

    public ResponsavelEmpresa() {
    }

    public ResponsavelEmpresa(LocalDate dataInicio, LocalDate dataFim, Usuario responsavelInicio, Usuario responsavelFim, Integer idResponsavelEmpresa, String nome, TipoResponsabilidade responsabilidade, Empresa empresa) {
        super(dataInicio, dataFim, responsavelInicio, responsavelFim);
        this.idResponsavelEmpresa = idResponsavelEmpresa;
        this.nome = nome;
        this.responsabilidade = responsabilidade;
        this.empresa = empresa;
    }

    public Integer getIdResponsavelEmpresa() {
        return idResponsavelEmpresa;
    }

    public void setIdResponsavelEmpresa(Integer idResponsavelEmpresa) {
        this.idResponsavelEmpresa = idResponsavelEmpresa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public TipoResponsabilidade getResponsabilidade() {
        return responsabilidade;
    }

    public void setResponsabilidade(TipoResponsabilidade responsabilidade) {
        this.responsabilidade = responsabilidade;
    }
}
