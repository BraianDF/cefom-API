package com.projeto.cefom.model;

import com.projeto.cefom.enums.TitularContato;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "telefones")
public class Telefone extends Vigencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idTelefone;
    @Column(name = "numero", length = 50, nullable = false)
    private String numero;
    @Enumerated(EnumType.STRING)
    @Column(name = "titular", nullable = false)
    private TitularContato titular;
    @ManyToOne
    @JoinColumn(name = "idAdolescente")
    private Adolescente adolescente;
    @ManyToOne
    @JoinColumn(name = "idEmpresa")
    private Empresa empresa;

    public Telefone() {
    }

    public Telefone(LocalDate dataInicio, LocalDate dataFim, Usuario responsavelInicio, Usuario responsavelFim, Integer idTelefone, String numero, TitularContato titular, Adolescente adolescente, Empresa empresa) {
        super(dataInicio, dataFim, responsavelInicio, responsavelFim);
        this.idTelefone = idTelefone;
        this.numero = numero;
        this.titular = titular;
        this.adolescente = adolescente;
        this.empresa = empresa;
    }

    public Integer getIdTelefone() {
        return idTelefone;
    }

    public void setIdTelefone(Integer idTelefone) {
        this.idTelefone = idTelefone;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public TitularContato getTitular() {
        return titular;
    }

    public void setTitular(TitularContato titular) {
        this.titular = titular;
    }

    public Adolescente getAdolescente() {
        return adolescente;
    }

    public void setAdolescente(Adolescente adolescente) {
        this.adolescente = adolescente;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}
