package com.projeto.cefom.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "entrevistas")
public class Entrevista extends Vigencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idEntrevista;
    @ManyToOne
    @JoinColumn(name = "idEmpresa")
    private Empresa empresa;
    @Column(name = "entrevistaCancelada")
    private Boolean entrevistaCancelada = false;
    @OneToMany(mappedBy = "entrevista", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VinculoEntrevistaMatricula> adolescentes = new ArrayList<>();

    public Entrevista() {
    }

    public Entrevista(LocalDate dataInicio, LocalDate dataFim, Usuario responsavelInicio, Usuario responsavelFim, Integer idEntrevista, Empresa empresa, Boolean entrevistaCancelada) {
        super(dataInicio, dataFim, responsavelInicio, responsavelFim);
        this.idEntrevista = idEntrevista;
        this.empresa = empresa;
        this.entrevistaCancelada = entrevistaCancelada;
    }

    public Integer getIdEntrevista() {
        return idEntrevista;
    }

    public void setIdEntrevista(Integer idEntrevista) {
        this.idEntrevista = idEntrevista;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Boolean getEntrevistaCancelada() {
        return entrevistaCancelada;
    }

    public void setEntrevistaCancelada(Boolean entrevistaCancelada) {
        this.entrevistaCancelada = entrevistaCancelada;
    }

    public List<VinculoEntrevistaMatricula> getAdolescentes() {
        return adolescentes;
    }

    public void setAdolescentes(List<VinculoEntrevistaMatricula> adolescentes) {
        this.adolescentes = adolescentes;
    }

    public void adicionarAdolescente(VinculoEntrevistaMatricula adolescente) {
        adolescentes.add(adolescente);
        adolescente.setEntrevista(this);
    }

    public void removerAdolescente(VinculoEntrevistaMatricula adolescente) {
        adolescentes.remove(adolescente);
        adolescente.setEntrevista(null);
    }
}
