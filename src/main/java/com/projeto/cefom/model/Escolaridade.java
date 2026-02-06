package com.projeto.cefom.model;

import com.projeto.cefom.enums.Periodo;
import com.projeto.cefom.enums.Serie;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "escolariedades")
public class Escolaridade extends Vigencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idEscolaridade;
    @Enumerated(EnumType.STRING)
    @Column(name = "serie", length = 15)
    private Serie serie;
    @Enumerated(EnumType.STRING)
    @Column(name = "periodo", length = 10)
    private Periodo periodo;
    @Column(name = "raEscolar", length = 20)
    private String raEscolar;
    @Column(name = "curso", length = 50)
    private String curso;
    @ManyToOne
    @JoinColumn(name = "idEscola")
    private Escola escola;

    @ManyToOne
    @JoinColumn(name = "idAdolescente")
    private Adolescente adolescente;

    public Escolaridade() {
    }

    public Escolaridade(LocalDate dataInicio, LocalDate dataFim, Usuario responsavelInicio, Usuario responsavelFim, Integer idEscolaridade, Serie serie, Periodo periodo, String raEscolar, String curso, Escola escola, Adolescente adolescente) {
        super(dataInicio, dataFim, responsavelInicio, responsavelFim);
        this.idEscolaridade = idEscolaridade;
        this.serie = serie;
        this.periodo = periodo;
        this.raEscolar = raEscolar;
        this.curso = curso;
        this.escola = escola;
        this.adolescente = adolescente;
    }

    public Integer getIdEscolaridade() {
        return idEscolaridade;
    }

    public void setIdEscolaridade(Integer idEscolaridade) {
        this.idEscolaridade = idEscolaridade;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public String getRaEscolar() {
        return raEscolar;
    }

    public void setRaEscolar(String raEscolar) {
        this.raEscolar = raEscolar;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public Escola getEscola() {
        return escola;
    }

    public void setEscola(Escola escola) {
        this.escola = escola;
    }

    public Adolescente getAdolescente() {
        return adolescente;
    }

    public void setAdolescente(Adolescente adolescente) {
        this.adolescente = adolescente;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

}
