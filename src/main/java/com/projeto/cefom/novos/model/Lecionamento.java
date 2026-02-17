package com.projeto.cefom.novos.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lecionamentos")
public class Lecionamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idLecionamento;
    @ManyToOne
    @JoinColumn(name = "idProfessor")
    private Professor professor;
    @ManyToOne
    @JoinColumn(name = "idDisciplina")
    private Disciplina disciplina;
    @OneToMany(mappedBy = "lecionamento", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Aula> aulas = new ArrayList<>();

    public Lecionamento() {
    }

    public Lecionamento(Integer idLecionamento, Professor professor, Disciplina disciplina) {
        this.idLecionamento = idLecionamento;
        this.professor = professor;
        this.disciplina = disciplina;
    }

    public Integer getIdLecionamento() {
        return idLecionamento;
    }

    public void setIdLecionamento(Integer idLecionamento) {
        this.idLecionamento = idLecionamento;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public List<Aula> getAulas() {
        return aulas;
    }

    public void setAulas(List<Aula> aulas) {
        this.aulas = aulas;
    }

    public void adicionarAula(Aula aula) {
        aulas.add(aula);
        aula.setLecionamento(this);
    }

    public void removerAula(Aula aula) {
        aulas.remove(aula);
        aula.setLecionamento(null);
    }
}
