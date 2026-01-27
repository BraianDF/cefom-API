package com.projeto.sistema.novos.model;

import com.projeto.sistema.enums.Situacao;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "turmas")
public class Turma implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idTurma;
    @Column(name = "nome", length = 50, nullable = false)
    private String nome;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private Situacao tipo;
    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participacao> alunos = new ArrayList<>();
    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Aula> aulas = new ArrayList<>();

    public Turma() {
    }

    public Turma(Integer idTurma, String nome, Situacao tipo) {
        this.idTurma = idTurma;
        this.nome = nome;
        this.tipo = tipo;
    }

    public Integer getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(Integer idTurma) {
        this.idTurma = idTurma;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Situacao getTipo() {
        return tipo;
    }

    public void setTipo(Situacao tipo) {
        this.tipo = tipo;
    }

    public List<Participacao> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Participacao> alunos) {
        this.alunos = alunos;
    }

    public List<Aula> getAulas() {
        return aulas;
    }

    public void setAulas(List<Aula> aulas) {
        this.aulas = aulas;
    }
}
