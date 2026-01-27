package com.projeto.sistema.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalTime;

@Entity
@Table(name = "vinculoEntrevistasMatriculas")
public class VinculoEntrevistaMatricula implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idVinculoEntrevistaMatricula;
    @ManyToOne
    @JoinColumn(name = "idEntrevista")
    private Entrevista entrevista;
    @ManyToOne
    @JoinColumn(name = "idMatricula")
    private Matricula matricula;
    @Column(name = "horarioEntrevista")
    private LocalTime horarioEntrevista;
    @Column(name = "situacao", length = 100)
    private String situacao;

    public VinculoEntrevistaMatricula() {
    }

    public VinculoEntrevistaMatricula(Integer idVinculoEntrevistaMatricula, Entrevista entrevista, Matricula matricula, LocalTime horarioEntrevista, String situacao) {
        this.idVinculoEntrevistaMatricula = idVinculoEntrevistaMatricula;
        this.entrevista = entrevista;
        this.matricula = matricula;
        this.horarioEntrevista = horarioEntrevista;
        this.situacao = situacao;
    }

    public Integer getIdVinculoEntrevistaMatricula() {
        return idVinculoEntrevistaMatricula;
    }

    public void setIdVinculoEntrevistaMatricula(Integer idVinculoEntrevistaMatricula) {
        this.idVinculoEntrevistaMatricula = idVinculoEntrevistaMatricula;
    }

    public Entrevista getEntrevista() {
        return entrevista;
    }

    public void setEntrevista(Entrevista entrevista) {
        this.entrevista = entrevista;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public LocalTime getHorarioEntrevista() {
        return horarioEntrevista;
    }

    public void setHorarioEntrevista(LocalTime horarioEntrevista) {
        this.horarioEntrevista = horarioEntrevista;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}
