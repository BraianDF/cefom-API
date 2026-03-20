package com.projeto.cefom.model;

import com.projeto.cefom.utils.TextoUtils;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "professores")
public class Professor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idProfessor;
    @Column(name = "nome", length = 50, nullable = false)
    private String nome;
    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lecionamento> disciplinas = new ArrayList<>();

    public Professor() {
    }

    public Professor(Integer idProfessor, String nome) {
        this.idProfessor = idProfessor;
        this.nome = nome;
    }

    public Integer getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(Integer idProfessor) {
        this.idProfessor = idProfessor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = TextoUtils.normalizar(nome);
    }

    public List<Lecionamento> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Lecionamento> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public void adicionarDisciplina(Lecionamento lecionamento) {
        disciplinas.add(lecionamento);
        lecionamento.setProfessor(this);
    }

    public void removerDisciplina(Lecionamento lecionamento) {
        disciplinas.remove(lecionamento);
        lecionamento.setProfessor(null);
    }
}
