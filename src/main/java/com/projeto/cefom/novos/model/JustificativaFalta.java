package com.projeto.cefom.novos.model;

import com.projeto.cefom.exceptions.RegraNegocioException;
import com.projeto.cefom.model.Matricula;
import com.projeto.cefom.model.Usuario;
import com.projeto.cefom.model.Vigencia;
import com.projeto.cefom.novos.enums.MotivoJustificativa;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "justificativasFaltas")
public class JustificativaFalta extends Vigencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idJustificativaFalta;
    @Enumerated(EnumType.STRING)
    @Column(name = "motivo", nullable = false)
    private MotivoJustificativa motivo;
    @Column(name = "qtdeDias", nullable = false)
    private Integer qtdeDias;
    @Column(name = "observacao", length = 100)
    private String observacao;
    @ManyToOne
    @JoinColumn(name = "idAluno")
    private Matricula aluno;
    @OneToMany(mappedBy = "justificativa", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Presenca> presencas = new ArrayList<>();
    @OneToMany(mappedBy = "justificativa", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<FaltaTrabalho> faltasTrabalhos = new ArrayList<>();

    public JustificativaFalta() {
    }

    public JustificativaFalta(LocalDate dataInicio, LocalDate dataFim, Usuario responsavelInicio, Usuario responsavelFim, Integer idJustificativaFalta, MotivoJustificativa motivo, Integer qtdeDias, String observacao, Matricula aluno) {
        super(dataInicio, dataFim, responsavelInicio, responsavelFim);
        this.idJustificativaFalta = idJustificativaFalta;
        this.motivo = motivo;
        this.qtdeDias = qtdeDias;
        this.observacao = observacao;
        this.aluno = aluno;
    }

    public Integer getIdJustificativaFalta() {
        return idJustificativaFalta;
    }

    public void setIdJustificativaFalta(Integer idJustificativaFalta) {
        this.idJustificativaFalta = idJustificativaFalta;
    }

    public MotivoJustificativa getMotivo() {
        return motivo;
    }

    public void setMotivo(MotivoJustificativa motivo) {
        this.motivo = motivo;
    }

    public Integer getQtdeDias() {
        return qtdeDias;
    }

    public void setQtdeDias(Integer qtdeDias) {
        this.qtdeDias = qtdeDias;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public List<Presenca> getPresencas() {
        return presencas;
    }

    public void setPresencas(List<Presenca> presencas) {
        this.presencas = presencas;
    }

    public List<FaltaTrabalho> getFaltasTrabalhos() {
        return faltasTrabalhos;
    }

    public void setFaltasTrabalhos(List<FaltaTrabalho> faltasTrabalhos) {
        this.faltasTrabalhos = faltasTrabalhos;
    }

    public Matricula getAluno() {
        return aluno;
    }

    public void setAluno(Matricula aluno) {
        this.aluno = aluno;
    }

    public void adicionarPresenca(Presenca presenca) {
        presencas.add(presenca);
        presenca.setJustificativa(this);
    }

    public void removerPresenca(Presenca presenca) {
        presencas.remove(presenca);
        presenca.setJustificativa(null);
    }

    public void adicionarFaltaTrabalho(FaltaTrabalho faltaTrabalho) {
        faltasTrabalhos.add(faltaTrabalho);
        faltaTrabalho.setJustificativa(this);
    }

    public void removerFaltaTrabalho(FaltaTrabalho faltaTrabalho) {
        faltasTrabalhos.remove(faltaTrabalho);
        faltaTrabalho.setJustificativa(null);
    }

    public void setDataFim(LocalDate dataInicio, Integer qtdeDias) {
        if (qtdeDias == null || qtdeDias <= 0) {
            throw new IllegalArgumentException("Quantidade de dias deve ser maior que zero");
        }
        super.setDataFim(dataInicio.plusDays(qtdeDias-1));
    }
}
