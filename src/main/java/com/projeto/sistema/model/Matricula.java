package com.projeto.sistema.model;

import com.projeto.sistema.enums.DesligamentoMatricula;
import com.projeto.sistema.enums.SituacaoMatricula;
import com.projeto.sistema.image.FotoAdolescente;
import com.projeto.sistema.novos.model.Participacao;
import com.projeto.sistema.novos.model.Presenca;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "matriculas")
@AttributeOverrides({
        @AttributeOverride(name = "dataInicio", column = @Column(name = "dataMatricula")),
        @AttributeOverride(name = "dataFim", column = @Column(name = "dataDesligamento"))
})
public class Matricula extends Vigencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idMatricula;
    @Column(name = "numMatricula")
    private Integer numMatricula;
    @Enumerated(EnumType.STRING)
    @Column(name = "situacaoMatricula", nullable = false)
    private SituacaoMatricula situacaoMatricula;
    @Enumerated(EnumType.STRING)
    @Column(name = "desligamento")
    private DesligamentoMatricula desligamento;
    @ManyToOne
    @JoinColumn(name = "idAdolescente")
    private Adolescente adolescente;
    @OneToMany(mappedBy = "matricula", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contrato> contratos = new ArrayList<>();
    @OneToMany(mappedBy = "matricula", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VinculoEntrevistaMatricula> entrevistas = new ArrayList<>();
    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Presenca> presencas = new ArrayList<>();
    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participacao> turmas = new ArrayList<>();

    @OneToOne(mappedBy = "matricula", cascade = CascadeType.ALL, orphanRemoval = true)
    private FotoAdolescente foto;

    public Matricula() {
    }

    public Matricula(LocalDate dataInicio, LocalDate dataFim, Usuario responsavelInicio, Usuario responsavelFim, Integer idMatricula, Integer numMatricula, SituacaoMatricula situacaoMatricula, DesligamentoMatricula desligamento, Adolescente adolescente, FotoAdolescente foto) {
        super(dataInicio, dataFim, responsavelInicio, responsavelFim);
        this.idMatricula = idMatricula;
        this.numMatricula = numMatricula;
        this.situacaoMatricula = situacaoMatricula;
        this.desligamento = desligamento;
        this.adolescente = adolescente;
        this.foto = foto;
    }

    public Integer getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(Integer idMatricula) {
        this.idMatricula = idMatricula;
    }

    public Integer getNumMatricula() {
        return numMatricula;
    }

    public void setNumMatricula(Integer numMatricula) {
        this.numMatricula = numMatricula;
    }

    public SituacaoMatricula getSituacaoMatricula() {
        return situacaoMatricula;
    }

    public void setSituacaoMatricula(SituacaoMatricula situacaoMatricula) {
        this.situacaoMatricula = situacaoMatricula;
    }

    public DesligamentoMatricula getDesligamento() {
        return desligamento;
    }

    public void setDesligamento(DesligamentoMatricula desligamento) {
        this.desligamento = desligamento;
    }

    public Adolescente getAdolescente() {
        return adolescente;
    }

    public void setAdolescente(Adolescente adolescente) {
        this.adolescente = adolescente;
    }

    public List<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(List<Contrato> contratos) {
        this.contratos = contratos;
    }

    public List<VinculoEntrevistaMatricula> getEntrevistas() {
        return entrevistas;
    }

    public void setEntrevistas(List<VinculoEntrevistaMatricula> entrevistas) {
        this.entrevistas = entrevistas;
    }

    public List<Presenca> getPresencas() {
        return presencas;
    }

    public void setPresencas(List<Presenca> presencas) {
        this.presencas = presencas;
    }

    public List<Participacao> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<Participacao> turmas) {
        this.turmas = turmas;
    }

    public void adicionarContrato(Contrato contrato) {
        contratos.add(contrato);
        contrato.setMatricula(this);
    }

    public void removerContrato(Contrato contrato) {
        contratos.remove(contrato);
        contrato.setMatricula(null);
    }

    public void adicionarContrato(VinculoEntrevistaMatricula entrevista) {
        entrevistas.add(entrevista);
        entrevista.setMatricula(this);
    }

    public void removerContrato(VinculoEntrevistaMatricula entrevista) {
        entrevistas.remove(entrevista);
        entrevista.setMatricula(null);
    }

    public void adicionarEntrevista(VinculoEntrevistaMatricula entrevista) {
        entrevistas.add(entrevista);
        entrevista.setMatricula(this);
    }

    public void removerEntrevista(VinculoEntrevistaMatricula entrevista) {
        entrevistas.remove(entrevista);
        entrevista.setMatricula(null);
    }

    public FotoAdolescente getFoto() {
        return foto;
    }

    public void setFoto(FotoAdolescente foto) {
        this.foto = foto;
    }

    public void adicionarFoto(FotoAdolescente foto) {
        this.setFoto(foto);
        foto.setMatricula(this);
    }

    public void removerFoto() {
        if (this.foto != null) {
            this.foto.setMatricula(null);
            this.foto = null;
        }
    }
}
