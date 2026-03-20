package com.projeto.cefom.model;

import com.projeto.cefom.utils.TextoUtils;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cargos")
public class Cargo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idCargo;
    @Column(name = "funcao", length = 50, nullable = false)
    private String funcao;
    @Column(name = "cbo", length = 10, nullable = false)
    private String cbo;
    @OneToMany(mappedBy = "cargo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VinculoCursoCargo> cursos = new ArrayList<>();
    @OneToMany(mappedBy = "cargo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VinculoContratoCargo> contratos = new ArrayList<>();

    public Cargo() {
    }

    public Cargo(Integer idCargo, String funcao, String cbo) {
        this.idCargo = idCargo;
        this.funcao = funcao;
        this.cbo = cbo;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = TextoUtils.normalizar(funcao);
    }

    public String getCbo() {
        return cbo;
    }

    public void setCbo(String cbo) {
        this.cbo = TextoUtils.manterSomenteNumeros(cbo);
    }

    public List<VinculoCursoCargo> getCursos() {
        return cursos;
    }

    public void setCursos(List<VinculoCursoCargo> cursos) {
        this.cursos = cursos;
    }

    public List<VinculoContratoCargo> getContratos() {
        return contratos;
    }

    public void setContratos(List<VinculoContratoCargo> contratos) {
        this.contratos = contratos;
    }

    public void adicionarCurso(VinculoCursoCargo curso) {
        cursos.add(curso);
        curso.setCargo(this);
    }

    public void removerCurso(VinculoCursoCargo curso) {
        cursos.remove(curso);
        curso.setCargo(null);
    }

    public void adicionarContrato(VinculoContratoCargo contrato) {
        contratos.add(contrato);
        contrato.setCargo(this);
    }

    public void removerContrato(VinculoContratoCargo contrato) {
        contratos.remove(contrato);
        contrato.setCargo(null);
    }
}
