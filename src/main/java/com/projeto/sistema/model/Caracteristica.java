package com.projeto.sistema.model;

import com.projeto.sistema.enums.CorCabelo;
import com.projeto.sistema.enums.CorOlhos;
import com.projeto.sistema.enums.CorPele;
import com.projeto.sistema.enums.Habilidade;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "caracteristicas")
public class Caracteristica extends Vigencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idCaracteristica;
    @Enumerated(EnumType.STRING)
    @Column(name = "corOlhos")
    private CorOlhos corOlhos;
    @Enumerated(EnumType.STRING)
    @Column(name = "corCabelo")
    private CorCabelo corCabelo;
    @Enumerated(EnumType.STRING)
    @Column(name = "corPele")
    private CorPele corPele;
    @Column(name = "altura", precision = 3, scale = 2)
    private BigDecimal altura;
    @Column(name = "peso", precision = 3, scale = 0)
    private BigDecimal peso;
    @Enumerated(EnumType.STRING)
    @Column(name = "habilidade")
    private Habilidade habilidade;
    @ManyToOne
    @JoinColumn(name = "idAdolescente")
    private Adolescente adolescente;

    public Caracteristica() {
    }

    public Caracteristica(LocalDate dataInicio, LocalDate dataFim, Usuario responsavelInicio, Usuario responsavelFim, Integer idCaracteristica, CorOlhos corOlhos, CorCabelo corCabelo, CorPele corPele, BigDecimal altura, BigDecimal peso, Habilidade habilidade, Adolescente adolescente) {
        super(dataInicio, dataFim, responsavelInicio, responsavelFim);
        this.idCaracteristica = idCaracteristica;
        this.corOlhos = corOlhos;
        this.corCabelo = corCabelo;
        this.corPele = corPele;
        this.altura = altura;
        this.peso = peso;
        this.habilidade = habilidade;
        this.adolescente = adolescente;
    }

    public Integer getIdCaracteristica() {
        return idCaracteristica;
    }

    public void setIdCaracteristica(Integer idCaracteristica) {
        this.idCaracteristica = idCaracteristica;
    }

    public CorOlhos getCorOlhos() {
        return corOlhos;
    }

    public void setCorOlhos(CorOlhos corOlhos) {
        this.corOlhos = corOlhos;
    }

    public CorCabelo getCorCabelo() {
        return corCabelo;
    }

    public void setCorCabelo(CorCabelo corCabelo) {
        this.corCabelo = corCabelo;
    }

    public CorPele getCorPele() {
        return corPele;
    }

    public void setCorPele(CorPele corPele) {
        this.corPele = corPele;
    }

    public BigDecimal getAltura() {
        return altura;
    }

    public void setAltura(BigDecimal altura) {
        this.altura = altura;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public Habilidade getHabilidade() {
        return habilidade;
    }

    public void setHabilidade(Habilidade habilidade) {
        this.habilidade = habilidade;
    }

    public Adolescente getAdolescente() {
        return adolescente;
    }

    public void setAdolescente(Adolescente adolescente) {
        this.adolescente = adolescente;
    }

}
