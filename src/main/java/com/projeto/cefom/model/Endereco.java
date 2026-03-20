package com.projeto.cefom.model;

import com.projeto.cefom.enums.Estado;
import com.projeto.cefom.enums.TipoResidencia;
import com.projeto.cefom.utils.TextoUtils;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "enderecos")
public class Endereco extends Vigencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idEndereco;
    @Column(name = "cep", length = 50, nullable = false)
    private String cep;
    @Column(name = "logradouro", length = 100, nullable = false)
    private String logradouro;
    @Column(name = "numero", length = 15, nullable = false)
    private String numero;
    @Column(name = "complemento", length = 50)
    private String complemento;
    @Column(name = "bairro", length = 100, nullable = false)
    private String bairro;
    @Column(name = "cidade", length = 50, nullable = false)
    private String cidade;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 50, nullable = false)
    private Estado estado;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipoResidencia")
    private TipoResidencia tipoResidencia;
    @ManyToOne
    @JoinColumn(name = "idAdolescente")
    private Adolescente adolescente;
    @ManyToOne
    @JoinColumn(name = "idEscola")
    private Escola escola;
    @ManyToOne
    @JoinColumn(name = "idEmpresa")
    private Empresa empresa;
    @ManyToOne
    @JoinColumn(name = "idTerritorio")
    private Territorio territorio;

    public Endereco() {
    }

    public Endereco(LocalDate dataInicio, LocalDate dataFim, Usuario responsavelInicio, Usuario responsavelFim, Integer idEndereco, String cep, String logradouro, String numero, String complemento, String bairro, String cidade, Estado estado, TipoResidencia tipoResidencia, Adolescente adolescente, Escola escola, Empresa empresa, Territorio territorio) {
        super(dataInicio, dataFim, responsavelInicio, responsavelFim);
        this.idEndereco = idEndereco;
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.tipoResidencia = tipoResidencia;
        this.adolescente = adolescente;
        this.escola = escola;
        this.empresa = empresa;
        this.territorio = territorio;
    }

    public Integer getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(Integer idEndereco) {
        this.idEndereco = idEndereco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = TextoUtils.manterSomenteNumeros(cep);
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = TextoUtils.normalizar(logradouro);
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        String num = TextoUtils.manterSomenteNumeros(numero);
        if (num.isBlank() || num == null) num = "SEM NÚMERO";
        this.numero = num;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = TextoUtils.normalizar(complemento);
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = TextoUtils.normalizar(bairro);
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = TextoUtils.normalizar(cidade);
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Territorio getTerritorio() {
        return territorio;
    }

    public void setTerritorio(Territorio territorio) {
        this.territorio = territorio;
    }

    public Adolescente getAdolescente() {
        return adolescente;
    }

    public void setAdolescente(Adolescente adolescente) {
        this.adolescente = adolescente;
    }

    public Escola getEscola() {
        return escola;
    }

    public void setEscola(Escola escola) {
        this.escola = escola;
    }

    public TipoResidencia getTipoResidencia() {
        return tipoResidencia;
    }

    public void setTipoResidencia(TipoResidencia tipoResidencia) {
        this.tipoResidencia = tipoResidencia;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}
