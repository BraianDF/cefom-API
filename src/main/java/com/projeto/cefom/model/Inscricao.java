package com.projeto.cefom.model;

import com.projeto.cefom.enums.FinalizacaoInscricao;
import com.projeto.cefom.image.FotoAdolescente;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "inscricoes")
@AttributeOverrides({
        @AttributeOverride(name = "dataInicio", column = @Column(name = "dataInscricao")),
        @AttributeOverride(name = "dataFim", column = @Column(name = "dataFinalizacao"))
})
public class Inscricao extends Vigencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idInscricao;
    @Column(name = "numInscricao")
    private Integer numInscricao;
    @Enumerated(EnumType.STRING)
    @Column(name = "finalizacao")
    private FinalizacaoInscricao finalizacao;
    @Column(name = "observacao", length = 100)
    private String observacao;
    @ManyToOne
    @JoinColumn(name = "idResponsavelInscricao")
    private Usuario responsavelInscricao;
    @ManyToOne
    @JoinColumn(name = "idAdolescente")
    private Adolescente adolescente;

    @OneToOne(mappedBy = "inscricao", cascade = CascadeType.ALL, orphanRemoval = true)
    private FotoAdolescente foto;

    public Inscricao() {
    }

    public Inscricao(LocalDate dataInicio, LocalDate dataFim, Usuario responsavelInicio, Usuario responsavelFim, Integer idInscricao, Integer numInscricao, FinalizacaoInscricao finalizacao, String observacao, Usuario responsavelInscricao, Adolescente adolescente, FotoAdolescente foto) {
        super(dataInicio, dataFim, responsavelInicio, responsavelFim);
        this.idInscricao = idInscricao;
        this.numInscricao = numInscricao;
        this.finalizacao = finalizacao;
        this.observacao = observacao;
        this.responsavelInscricao = responsavelInscricao;
        this.adolescente = adolescente;
        this.foto = foto;
    }

    public Integer getIdInscricao() {
        return idInscricao;
    }

    public void setIdInscricao(Integer idInscricao) {
        this.idInscricao = idInscricao;
    }

    public Integer getNumInscricao() {
        return numInscricao;
    }

    public void setNumInscricao(Integer numInscricao) {
        this.numInscricao = numInscricao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Usuario getResponsavelInscricao() {
        return responsavelInscricao;
    }

    public void setResponsavelInscricao(Usuario responsavelInscricao) {
        this.responsavelInscricao = responsavelInscricao;
    }

    public FinalizacaoInscricao getFinalizacao() {
        return finalizacao;
    }

    public void setFinalizacao(FinalizacaoInscricao finalizacao) {
        this.finalizacao = finalizacao;
    }

    public Adolescente getAdolescente() {
        return adolescente;
    }

    public void setAdolescente(Adolescente adolescente) {
        this.adolescente = adolescente;
    }

    public FotoAdolescente getFoto() {
        return foto;
    }

    public void setFoto(FotoAdolescente foto) {
        this.foto = foto;
    }

    public void adicionarFoto(FotoAdolescente foto) {
        this.setFoto(foto);
        foto.setInscricao(this);
    }

    public void removerFoto() {
        if (this.foto != null) {
            this.foto.setInscricao(null);
            this.foto = null;
        }
    }

}
