package com.projeto.cefom.image.model;

import com.projeto.cefom.model.Inscricao;
import com.projeto.cefom.model.Matricula;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "fotosAdolescentes")
public class FotoAdolescente extends Arquivo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idFotoAdolescente;
    @OneToOne
    @JoinColumn(name = "idMatricula")
    private Matricula matricula;
    @OneToOne
    @JoinColumn(name = "idInscricao")
    private Inscricao inscricao;

    public FotoAdolescente() {
    }

    public FotoAdolescente(String nomeArquivo, String tipoArquivo, long tamanhoArquivo, String caminhoArquivo, Integer idFotoAdolescente, Matricula matricula, Inscricao inscricao) {
        super(nomeArquivo, tipoArquivo, tamanhoArquivo, caminhoArquivo);
        this.idFotoAdolescente = idFotoAdolescente;
        this.matricula = matricula;
        this.inscricao = inscricao;
    }

    public Integer getIdFotoAdolescente() {
        return idFotoAdolescente;
    }

    public void setIdFotoAdolescente(Integer idFotoAdolescente) {
        this.idFotoAdolescente = idFotoAdolescente;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public Inscricao getInscricao() {
        return inscricao;
    }

    public void setInscricao(Inscricao inscricao) {
        this.inscricao = inscricao;
    }

}