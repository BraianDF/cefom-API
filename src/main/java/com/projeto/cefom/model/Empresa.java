package com.projeto.cefom.model;

import com.projeto.cefom.enums.TipoContratacao;
import com.projeto.cefom.enums.TipoEmpresa;
import com.projeto.cefom.utils.TextoUtils;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "empresas")
public class Empresa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idEmpresa;
    private Integer numConvenio;
    @Column(name = "razaoSocial", length = 50, nullable = false)
    private String razaoSocial;
    @Column(name = "nomeFantasia", length = 50, nullable = false)
    private String nomeFantasia;
    @Column(name = "apelido", length = 50, nullable = false)
    private String apelido;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipoEmpresa")
    private TipoEmpresa tipoEmpresa;
    @Enumerated(EnumType.STRING)
    @Column(name = "contratacaoPadrao")
    private TipoContratacao contratacaoPadrao;

    @OneToOne(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private Documento documento;
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResponsavelEmpresa> responsaveis = new ArrayList<>();
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Endereco> enderecos = new ArrayList<>();
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Email> emails = new ArrayList<>();
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Telefone> telefones = new ArrayList<>();
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Entrevista> entrevistas = new ArrayList<>();
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contrato> contratos = new ArrayList<>();
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaxaAdministrativa> taxasAdministrativas = new ArrayList<>();

    public Empresa() {
    }

    public Empresa(Integer idEmpresa, Integer numConvenio, String razaoSocial, String nomeFantasia, String apelido, TipoEmpresa tipoEmpresa, TipoContratacao contratacaoPadrao, Documento documento) {
        this.idEmpresa = idEmpresa;
        this.numConvenio = numConvenio;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.apelido = apelido;
        this.tipoEmpresa = tipoEmpresa;
        this.contratacaoPadrao = contratacaoPadrao;
        this.documento = documento;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = TextoUtils.normalizar(razaoSocial);
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = TextoUtils.normalizar(nomeFantasia);
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = TextoUtils.normalizar(apelido);
    }

    public TipoContratacao getContratacaoPadrao() {
        return contratacaoPadrao;
    }

    public void setContratacaoPadrao(TipoContratacao contratacaoPadrao) {
        this.contratacaoPadrao = contratacaoPadrao;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public List<ResponsavelEmpresa> getResponsaveis() {
        return responsaveis;
    }

    public void setResponsaveis(List<ResponsavelEmpresa> responsaveis) {
        this.responsaveis = responsaveis;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public List<Email> getEmails() {
        return emails;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }

    public List<Entrevista> getEntrevistas() {
        return entrevistas;
    }

    public void setEntrevistas(List<Entrevista> entrevistas) {
        this.entrevistas = entrevistas;
    }

    public Integer getNumConvenio() {
        return numConvenio;
    }

    public void setNumConvenio(Integer numConvenio) {
        this.numConvenio = numConvenio;
    }

    public TipoEmpresa getTipoEmpresa() {
        return tipoEmpresa;
    }

    public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }

    public List<TaxaAdministrativa> getTaxasAdministrativas() {
        return taxasAdministrativas;
    }

    public void setTaxasAdministrativas(List<TaxaAdministrativa> taxasAdministrativas) {
        this.taxasAdministrativas = taxasAdministrativas;
    }

    public List<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(List<Contrato> contratos) {
        this.contratos = contratos;
    }

    public void adicionarDocumento(Documento documento) {
        this.setDocumento(documento);
        documento.setEmpresa(this);
    }

    public void removerDocumento() {
        if (this.documento != null) {
            this.documento.setEmpresa(null);
            this.documento = null;
        }
    }

    public void adicionarEndereco(Endereco endereco) {
        enderecos.add(endereco);
        endereco.setEmpresa(this);
    }

    public void removerEndereco(Endereco endereco) {
        enderecos.remove(endereco);
        endereco.setEmpresa(null);
    }

    public void adicionarEmail(Email email) {
        emails.add(email);
        email.setEmpresa(this);
    }

    public void removerEmail(Email email) {
        emails.remove(email);
        email.setEmpresa(null);
    }

    public void adicionarTelefone(Telefone telefone) {
        telefones.add(telefone);
        telefone.setEmpresa(this);
    }

    public void removerTelefone(Telefone telefone) {
        telefones.remove(telefone);
        telefone.setEmpresa(null);
    }

    public void adicionarResponsavel(ResponsavelEmpresa responsavel) {
        responsaveis.add(responsavel);
        responsavel.setEmpresa(this);
    }

    public void removerResponsavel(ResponsavelEmpresa responsavel) {
        responsaveis.remove(responsavel);
        responsavel.setEmpresa(null);
    }

    public void adicionarEntrevista(Entrevista entrevista) {
        entrevistas.add(entrevista);
        entrevista.setEmpresa(this);
    }

    public void removerEntrevista(Entrevista entrevista) {
        entrevistas.remove(entrevista);
        entrevista.setEmpresa(null);
    }

    public void adicionarTaxaAdministrativa(TaxaAdministrativa taxa) {
        taxasAdministrativas.add(taxa);
        taxa.setEmpresa(this);
    }

    public void removerTaxaAdministrativa(TaxaAdministrativa taxa) {
        taxasAdministrativas.remove(taxa);
        taxa.setEmpresa(null);
    }

    public void adicionarContrato(Contrato contrato) {
        contratos.add(contrato);
        contrato.setEmpresa(this);
    }

    public void removerContrato(Contrato contrato) {
        contratos.remove(contrato);
        contrato.setEmpresa(null);
    }
}