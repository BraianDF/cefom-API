package com.projeto.cefom.novos.model;

import com.projeto.cefom.model.Usuario;
import com.projeto.cefom.model.Vigencia;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lecionamentos")
public class Lecionamento extends Vigencia implements Serializable {
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
    @OneToMany(mappedBy = "lecionamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Aula> aulas = new ArrayList<>();

    public Lecionamento() {
    }

    public Lecionamento(LocalDate dataInicio, LocalDate dataFim, Usuario responsavelInicio, Usuario responsavelFim, Integer idLecionamento, Professor professor, Disciplina disciplina) {
        super(dataInicio, dataFim, responsavelInicio, responsavelFim);
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
}
