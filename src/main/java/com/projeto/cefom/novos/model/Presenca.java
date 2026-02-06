package com.projeto.cefom.novos.model;

import com.projeto.cefom.model.Matricula;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "presencas")
public class Presenca implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idPresenca;
    @Column(name = "avaliacao", nullable = false)
    private Integer avaliacao; //(0 a 3)
    @Column(name = "observacao", length = 100)
    private String observacao;
    @ManyToOne
    @JoinColumn(name = "idAula")
    private Aula aula;
    @ManyToOne
    @JoinColumn(name = "idAluno")
    private Matricula aluno;
    @ManyToOne
    @JoinColumn(name = "idJustificativa")
    private JustificativaFalta justificativa;
    @Transient
    private Boolean justificada;

    public Presenca() {
    }

    public Presenca(Integer idPresenca, Integer avaliacao, String observacao, Aula aula, Matricula aluno, JustificativaFalta justificativa, Boolean justificada) {
        this.idPresenca = idPresenca;
        this.avaliacao = avaliacao;
        this.observacao = observacao;
        this.aula = aula;
        this.aluno = aluno;
        this.justificativa = justificativa;
        this.justificada = justificada;
    }

    public Integer getIdPresenca() {
        return idPresenca;
    }

    public void setIdPresenca(Integer idPresenca) {
        this.idPresenca = idPresenca;
    }

    public Integer getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Integer avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public Matricula getAluno() {
        return aluno;
    }

    public void setAluno(Matricula aluno) {
        this.aluno = aluno;
    }

    public JustificativaFalta getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(JustificativaFalta justificativa) {
        this.justificativa = justificativa;
    }

    public Boolean getJustificada() {
        return justificada;
    }

    public void setJustificada(Boolean justificada) {
        this.justificada = justificada;
    }
}
