package com.projeto.cefom.model;

import com.projeto.cefom.enums.TitularContato;
import com.projeto.cefom.utils.TextoUtils;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "emails")
public class Email extends Vigencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idEmail;
    @Column(name = "email", length = 50, nullable = false)
    private String email;
    @Column(name = "titular", length = 50, nullable = false)
    private TitularContato titular;
    @ManyToOne
    @JoinColumn(name = "idAdolescente")
    private Adolescente adolescente;
    @ManyToOne
    @JoinColumn(name = "idEmpresa")
    private Empresa empresa;

    public Email() {
    }

    public Email(LocalDate dataInicio, LocalDate dataFim, Usuario responsavelInicio, Usuario responsavelFim, Integer idEmail, String email, TitularContato titular, Adolescente adolescente, Empresa empresa) {
        super(dataInicio, dataFim, responsavelInicio, responsavelFim);
        this.idEmail = idEmail;
        this.email = email;
        this.titular = titular;
        this.adolescente = adolescente;
        this.empresa = empresa;
    }

    public Integer getIdEmail() {
        return idEmail;
    }

    public void setIdEmail(Integer idEmail) {
        this.idEmail = idEmail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = TextoUtils.normalizar(email);
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
