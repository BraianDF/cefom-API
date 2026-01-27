package com.projeto.sistema.model;

import com.projeto.sistema.enums.DesligamentoMatricula;
import com.projeto.sistema.enums.SituacaoContrato;
import com.projeto.sistema.enums.TipoContratacao;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contratos")
public class Contrato extends Vigencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idContrato;
    @Column(name = "dataTermino")
    private LocalDate dataTermino;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipoContratacao")
    private TipoContratacao tipoContratacao;
    //Pode virar herança dependendo de como crescer a classe
    @Enumerated(EnumType.STRING)
    @Column(name = "desligamento")
    private DesligamentoMatricula desligamento;
    @Column(name = "efetivado")
    private Boolean efetivado;
    @ManyToOne
    @JoinColumn(name = "idMatricula")
    private Matricula matricula;
    @ManyToOne
    @JoinColumn(name = "idEmpresa")
    private Empresa empresa;
    @Transient
    private SituacaoContrato situacaoContrato;
    @Transient
    private LocalDate dataSituacaoContrato;
    @OneToMany(mappedBy = "contrato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Afastamento> afastamentos = new ArrayList<>();
    @OneToMany(mappedBy = "contrato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VinculoContratoCargo> cargos = new ArrayList<>();
    @OneToMany(mappedBy = "contrato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Salario> salarios = new ArrayList<>();
    @OneToMany(mappedBy = "contrato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JornadaTrabalho> jornadasTrabalho = new ArrayList<>();

    public Contrato() {
    }

    public Contrato(LocalDate dataInicio, LocalDate dataFim, Usuario responsavelInicio, Usuario responsavelFim, Integer idContrato, LocalDate dataTermino, TipoContratacao tipoContratacao, DesligamentoMatricula desligamento, Boolean efetivado, Matricula matricula, Empresa empresa, SituacaoContrato situacaoContrato, LocalDate dataSituacaoContrato) {
        super(dataInicio, dataFim, responsavelInicio, responsavelFim);
        this.idContrato = idContrato;
        this.dataTermino = dataTermino;
        this.tipoContratacao = tipoContratacao;
        this.desligamento = desligamento;
        this.efetivado = efetivado;
        this.matricula = matricula;
        this.empresa = empresa;
        this.situacaoContrato = situacaoContrato;
        this.dataSituacaoContrato = dataSituacaoContrato;
    }

    public Integer getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(Integer idContrato) {
        this.idContrato = idContrato;
    }

    public LocalDate getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(LocalDate dataTermino) {
        this.dataTermino = dataTermino;
    }

    public TipoContratacao getTipoContratacao() {
        return tipoContratacao;
    }

    public void setTipoContratacao(TipoContratacao tipoContratacao) {
        this.tipoContratacao = tipoContratacao;
    }

    public DesligamentoMatricula getDesligamento() {
        return desligamento;
    }

    public void setDesligamento(DesligamentoMatricula desligamento) {
        this.desligamento = desligamento;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<Afastamento> getAfastamentos() {
        return afastamentos;
    }

    public void setAfastamentos(List<Afastamento> afastamentos) {
        this.afastamentos = afastamentos;
    }

    public List<VinculoContratoCargo> getCargos() {
        return cargos;
    }

    public void setCargos(List<VinculoContratoCargo> cargos) {
        this.cargos = cargos;
    }

    public List<Salario> getSalarios() {
        return salarios;
    }

    public void setSalarios(List<Salario> salarios) {
        this.salarios = salarios;
    }

    public List<JornadaTrabalho> getJornadasTrabalho() {
        return jornadasTrabalho;
    }

    public void setJornadasTrabalho(List<JornadaTrabalho> jornadasTrabalho) {
        this.jornadasTrabalho = jornadasTrabalho;
    }

    public SituacaoContrato getSituacaoContrato() {
        return getSituacaoContratoEm(LocalDate.now());
    }

    public SituacaoContrato getSituacaoContratoEm(LocalDate data) {
        if (isNaoIniciadoEm(data)) {
            return SituacaoContrato.NAO_INICIADO;
        }
        if (isConcluidoEm(data)) {
            return SituacaoContrato.CONCLUIDO;
        }
        if (isAfastadoEm(data)) {
            return SituacaoContrato.AFASTADO_SUSPENSO;
        }
        if (isMatriculadoEm(data)) {
            return SituacaoContrato.MATRICULADO;
        }
        if (isRescindidoEm(data)) {
            return SituacaoContrato.RESCINDIDO;
        }
        throw new IllegalStateException("Situação não mapeada para a data " + data+".");
    }

    private boolean isNaoIniciadoEm(LocalDate data) {
        return // contrato não ativo na data
                (getDataInicio() == null || data.isBefore(getDataInicio()));
    }

    private boolean isAfastadoEm(LocalDate data) {
        return // contrato ativa na data
                !getDataInicio().isAfter(data) &&
                (getDataFim() == null || getDataFim().isAfter(data)) &&

                // existe afastamento desse contrato ativo na data e já iniciado
                getAfastamentos().stream().anyMatch(a ->
                        (a.getDataFim() == null || a.getDataFim().isAfter(data)) &&
                                !a.getDataInicio().isAfter(data)
                );
    }

    private boolean isMatriculadoEm(LocalDate data) {
        return // contrato ativa na data
                !getDataInicio().isAfter(data) &&
                (getDataFim() == null || getDataFim().isAfter(data));
    }

    private boolean isConcluidoEm(LocalDate data) {
        return // contrato encerrado na data
                (getDataFim() != null && !getDataFim().isAfter(data)) &&

                //o motivo do desligamento foi término de contrato
                        getDesligamento() == DesligamentoMatricula.TERMINO_CONTRATO;
    }

    private boolean isRescindidoEm(LocalDate data) {
        return // contrato encerrado na data
                (getDataFim() != null && !getDataFim().isAfter(data)) &&

                        //o motivo do desligamento não foi término de contrato
                        getDesligamento() != DesligamentoMatricula.TERMINO_CONTRATO;
    }

    public void setSituacaoContrato(SituacaoContrato situacaoContrato) {
        this.situacaoContrato = situacaoContrato;
    }

    public LocalDate getDataSituacaoContrato() {
        return dataSituacaoContrato;
    }

    public void setDataSituacaoContrato(LocalDate dataSituacaoContrato) {
        this.dataSituacaoContrato = dataSituacaoContrato;
    }

    public Boolean getEfetivado() {
        return efetivado;
    }

    public void setEfetivado(Boolean efetivado) {
        this.efetivado = efetivado;
    }

    public void adicionarCargo(VinculoContratoCargo cargo) {
        cargos.add(cargo);
        cargo.setContrato(this);
    }

    public void removerCargo(VinculoContratoCargo cargo) {
        cargos.remove(cargo);
        cargo.setContrato(null);
    }

    public void adicionarAfastamento(Afastamento afastamento) {
        afastamentos.add(afastamento);
        afastamento.setContrato(this);
    }

    public void removerAfastamento(Afastamento afastamento) {
        afastamentos.remove(afastamento);
        afastamento.setContrato(null);
    }

    public void adicionarSalario(Salario salario) {
        salarios.add(salario);
        salario.setContrato(this);
    }

    public void removerSalario(Salario salario) {
        salarios.remove(salario);
        salario.setContrato(null);
    }

    public void adicionarJornadaTrabalho(JornadaTrabalho jornadaTrabalho) {
        jornadasTrabalho.add(jornadaTrabalho);
        jornadaTrabalho.setContrato(this);
    }

    public void removerJornadaTrabalho(JornadaTrabalho jornadaTrabalho) {
        jornadasTrabalho.remove(jornadaTrabalho);
        jornadaTrabalho.setContrato(null);
    }
}
