package com.projeto.sistema.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "vinculoCursosCargos")
public class VinculoCursoCargo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idCargoCurso;
    @ManyToOne
    @JoinColumn(name = "idCargo")
    private Cargo cargo;
    @ManyToOne
    @JoinColumn(name = "idCurso")
    private Curso curso;

    public VinculoCursoCargo() {
    }

    public VinculoCursoCargo(Integer idCargoCurso, Cargo cargo, Curso curso) {
        this.idCargoCurso = idCargoCurso;
        this.cargo = cargo;
        this.curso = curso;
    }

    public Integer getIdCargoCurso() {
        return idCargoCurso;
    }

    public void setIdCargoCurso(Integer idCargoCurso) {
        this.idCargoCurso = idCargoCurso;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}
