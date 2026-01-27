package com.projeto.sistema.novos.model;

import com.projeto.sistema.model.Matricula;
import com.projeto.sistema.model.Usuario;
import com.projeto.sistema.model.Vigencia;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "participacoes")
public class Participacao extends Vigencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idParticipacao;
    @ManyToOne
    @JoinColumn(name = "idAluno")
    private Matricula aluno;
    @ManyToOne
    @JoinColumn(name = "idTurma")
    private Turma turma;

    public Participacao() {
    }

    public Participacao(LocalDate dataInicio, LocalDate dataFim, Usuario responsavelInicio, Usuario responsavelFim, Integer idParticipacao, Matricula aluno, Turma turma) {
        super(dataInicio, dataFim, responsavelInicio, responsavelFim);
        this.idParticipacao = idParticipacao;
        this.aluno = aluno;
        this.turma = turma;
    }

    public Integer getIdParticipacao() {
        return idParticipacao;
    }

    public void setIdParticipacao(Integer idParticipacao) {
        this.idParticipacao = idParticipacao;
    }

    public Matricula getAluno() {
        return aluno;
    }

    public void setAluno(Matricula aluno) {
        this.aluno = aluno;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }
}
