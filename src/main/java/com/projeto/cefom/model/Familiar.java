package com.projeto.cefom.model;

import com.projeto.cefom.enums.EscolaridadeFamiliar;
import com.projeto.cefom.enums.EstadoCivil;
import com.projeto.cefom.enums.Naturalidade;
import com.projeto.cefom.enums.Parentesco;
import com.projeto.cefom.utils.TextoUtils;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "familiares")
public class Familiar extends Vigencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idFamiliar;
    @Column(name = "nome", length = 50)
    private String nome;
    @Enumerated(EnumType.STRING)
    @Column(name = "parentesco")
    private Parentesco parentesco;
    @Column(name = "idade")
    private Integer idade;
    @Enumerated(EnumType.STRING)
    @Column(name = "escolaridade")
    private EscolaridadeFamiliar escolaridade;
    @Column(name = "profissao", length = 30)
    private String profissao;
    @Column(name = "localTrabalho", length = 30)
    private String localTrabalho;
    @Column(name = "renda", precision = 8, scale = 2)
    private BigDecimal renda;
    @Column(name = "reside")
    private Boolean reside;
    @Enumerated(EnumType.STRING)
    @Column(name = "naturalidade")
    private Naturalidade naturalidade;
    @Enumerated(EnumType.STRING)
    @Column(name = "estadoCivil")
    private EstadoCivil estadoCivil;
    @OneToOne(mappedBy = "familiar", cascade = CascadeType.ALL, orphanRemoval = true)
    private Documento documento;
    @ManyToOne
    @JoinColumn(name = "idAdolescente")
    private Adolescente adolescente;

    public Familiar() {
    }

    public Familiar(LocalDate dataInicio, LocalDate dataFim, Usuario responsavelInicio, Usuario responsavelFim, Integer idFamiliar, String nome, Parentesco parentesco, Integer idade, EscolaridadeFamiliar escolaridade, String profissao, String localTrabalho, BigDecimal renda, Boolean reside, Naturalidade naturalidade, EstadoCivil estadoCivil, Documento documento, Adolescente adolescente) {
        super(dataInicio, dataFim, responsavelInicio, responsavelFim);
        this.idFamiliar = idFamiliar;
        this.nome = nome;
        this.parentesco = parentesco;
        this.idade = idade;
        this.escolaridade = escolaridade;
        this.profissao = profissao;
        this.localTrabalho = localTrabalho;
        this.renda = renda;
        this.reside = reside;
        this.naturalidade = naturalidade;
        this.estadoCivil = estadoCivil;
        this.documento = documento;
        this.adolescente = adolescente;
    }

    public Integer getIdFamiliar() {
        return idFamiliar;
    }

    public void setIdFamiliar(Integer idFamiliar) {
        this.idFamiliar = idFamiliar;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = TextoUtils.normalizar(nome);
    }

    public Parentesco getParentesco() {
        return parentesco;
    }

    public void setParentesco(Parentesco parentesco) {
        this.parentesco = parentesco;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public EscolaridadeFamiliar getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(EscolaridadeFamiliar escolaridade) {
        this.escolaridade = escolaridade;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = TextoUtils.normalizar(profissao);
    }

    public String getLocalTrabalho() {
        return localTrabalho;
    }

    public void setLocalTrabalho(String localTrabalho) {
        this.localTrabalho = TextoUtils.normalizar(localTrabalho);
    }

    public BigDecimal getRenda() {
        return renda != null ? renda : BigDecimal.ZERO;
    }

    public void setRenda(BigDecimal renda) {
        this.renda = renda;
    }

    public Boolean getReside() {
        return reside;
    }

    public void setReside(Boolean reside) {
        this.reside = reside;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public Adolescente getAdolescente() {
        return adolescente;
    }

    public void setAdolescente(Adolescente adolescente) {
        this.adolescente = adolescente;
    }

    public Naturalidade getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(Naturalidade naturalidade) {
        this.naturalidade = naturalidade;
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public boolean isResponsavel() {
        return documento != null;
    }

    public void adicionarDocumento(Documento documento) {
        this.setDocumento(documento);
        documento.setFamiliar(this);
    }

    public void removerDocumento() {
        if (this.documento != null) {
            this.documento.setFamiliar(null);
            this.documento = null;
        }
    }
}
