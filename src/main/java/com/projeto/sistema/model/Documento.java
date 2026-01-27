package com.projeto.sistema.model;

import com.projeto.sistema.exceptions.RegraNegocioException;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "documentos")
public class Documento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idDocumento;
    @Column(name = "cpf", length = 15)
    private String cpf;
    @Column(name = "cnpj", length = 15)
    private String cnpj;
    @Column(name = "ctps", length = 15)
    private String ctps;
    @Column(name = "rg", length = 15)
    private String rg;
    @Column(name = "dataEmissaoRG")
    private LocalDate dataEmissaoRG;
    @Column(name = "nis", length = 15)
    private String nis;
    @Column(name = "sus", length = 20)
    private String sus;
    @Column(name = "tituloEleitor", length = 15)
    private String tituloEleitor;
    @Column(name = "zonaTE", length = 5)
    private String zonaTE;
    @Column(name = "secaoTE", length = 5)
    private String secaoTE;
    @Column(name = "cnh", length = 15)
    private String cnh;
    @Column(name = "categoriaCNH", length = 5)
    private String categoriaCNH;
    @Column(name = "reservista", length = 15)
    private String reservista;

    @OneToOne
    @JoinColumn(name = "idAdolescente")
    private Adolescente adolescente;
    @OneToOne
    @JoinColumn(name = "idFamiliar")
    private Familiar familiar;

    @OneToOne
    @JoinColumn(name = "idEmpresa")
    private Empresa empresa;

    public Documento() {
    }

    public Documento(Integer idDocumento, String cpf, String cnpj, String ctps, String rg, LocalDate dataEmissaoRG, String nis, String sus, String tituloEleitor, String zonaTE, String secaoTE, String cnh, String categoriaCNH, String reservista, Adolescente adolescente, Familiar familiar, Empresa empresa) {
        this.idDocumento = idDocumento;
        this.cpf = cpf;
        this.cnpj = cnpj;
        this.ctps = ctps;
        this.rg = rg;
        this.dataEmissaoRG = dataEmissaoRG;
        this.nis = nis;
        this.sus = sus;
        this.tituloEleitor = tituloEleitor;
        this.zonaTE = zonaTE;
        this.secaoTE = secaoTE;
        this.cnh = cnh;
        this.categoriaCNH = categoriaCNH;
        this.reservista = reservista;
        this.adolescente = adolescente;
        this.familiar = familiar;
        this.empresa = empresa;
    }

    public Integer getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = limparDocumento(cpf);
    }

    public String getCtps() {
        return ctps;
    }

    public void setCtps(String ctps) {
        this.ctps = limparDocumento(ctps);
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public LocalDate getDataEmissaoRG() {
        return dataEmissaoRG;
    }

    public void setDataEmissaoRG(LocalDate dataEmissaoRG) {
        this.dataEmissaoRG = dataEmissaoRG;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public String getSus() {
        return sus;
    }

    public void setSus(String sus) {
        this.sus = sus;
    }

    public String getTituloEleitor() {
        return tituloEleitor;
    }

    public void setTituloEleitor(String tituloEleitor) {
        this.tituloEleitor = tituloEleitor;
    }

    public String getZonaTE() {
        return zonaTE;
    }

    public void setZonaTE(String zonaTE) {
        this.zonaTE = zonaTE;
    }

    public String getSecaoTE() {
        return secaoTE;
    }

    public void setSecaoTE(String secaoTE) {
        this.secaoTE = secaoTE;
    }

    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public String getCategoriaCNH() {
        return categoriaCNH;
    }

    public void setCategoriaCNH(String categoriaCNH) {
        this.categoriaCNH = categoriaCNH;
    }

    public String getReservista() {
        return reservista;
    }

    public void setReservista(String reservista) {
        this.reservista = reservista;
    }

    public Adolescente getAdolescente() {
        return adolescente;
    }

    public void setAdolescente(Adolescente adolescente) {
        this.adolescente = adolescente;
    }

    public Familiar getFamiliar() {
        return familiar;
    }

    public void setFamiliar(Familiar familiar) {
        this.familiar = familiar;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = limparDocumento(cnpj);
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String limparDocumento(String documento) {
        if (documento == null || documento.isBlank()) {
            throw new RegraNegocioException("Documento não pode ser vazio.");
        }
        return documento.replaceAll("\\D", "");
    }

    public boolean isCnpj(String documento) {
        /*
        if (documento == null || documento.isBlank()) {
            throw new RegraNegocioException("Documento não pode ser vazio.");
        }
        */
        return limparDocumento(documento).length() == 14;
    }
}
