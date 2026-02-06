package com.projeto.cefom.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "vinculoContratosCargos")
public class VinculoContratoCargo extends Vigencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idCargoContrato;
    @ManyToOne
    @JoinColumn(name = "idCargo")
    private Cargo cargo;
    @ManyToOne
    @JoinColumn(name = "idContrato")
    private Contrato contrato;

    public VinculoContratoCargo() {
    }

    public VinculoContratoCargo(LocalDate dataInicio, LocalDate dataFim, Usuario responsavelInicio, Usuario responsavelFim, Integer idCargoContrato, Cargo cargo, Contrato contrato) {
        super(dataInicio, dataFim, responsavelInicio, responsavelFim);
        this.idCargoContrato = idCargoContrato;
        this.cargo = cargo;
        this.contrato = contrato;
    }

    public Integer getIdCargoContrato() {
        return idCargoContrato;
    }

    public void setIdCargoContrato(Integer idCargoContrato) {
        this.idCargoContrato = idCargoContrato;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }
}
