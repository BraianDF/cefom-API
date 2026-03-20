package com.projeto.cefom.model;

import com.projeto.cefom.utils.TextoUtils;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "disciplinas")
public class Disciplina implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idDisciplina;
    @Column(name = "nome", length = 50, nullable = false)
    private String nome;
    @OneToMany(mappedBy = "disciplina", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lecionamento> professores = new ArrayList<>();

    public Disciplina() {
    }

    public Disciplina(Integer idDisciplina, String nome) {
        this.idDisciplina = idDisciplina;
        this.nome = nome;
    }

    public Integer getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(Integer idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = TextoUtils.normalizar(nome);
    }

    public List<Lecionamento> getProfessores() {
        return professores;
    }

    public void setProfessores(List<Lecionamento> professores) {
        this.professores = professores;
    }

    public void adicionarProfessor(Lecionamento lecionamento) {
        professores.add(lecionamento);
        lecionamento.setDisciplina(this);
    }

    public void removerProfessor(Lecionamento lecionamento) {
        professores.remove(lecionamento);
        lecionamento.setDisciplina(null);
    }
}
