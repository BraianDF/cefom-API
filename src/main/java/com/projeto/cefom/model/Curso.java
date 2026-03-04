package com.projeto.cefom.model;

import com.projeto.cefom.enums.ModalidadeCurso;
import com.projeto.cefom.utils.TextoUtils;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cursos")
public class Curso extends Vigencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idCurso;
    @Column(name = "nomeCurso", length = 150, nullable = false)
    private String nomeCurso;
    @Column(name = "nomePrograma", length = 150, nullable = false)
    private String nomePrograma;
    @Enumerated(EnumType.STRING)
    @Column(name = "modalidadeCurso")
    private ModalidadeCurso modalidadeCurso;
    @Column(name = "protocoloAprovacao", length = 50, nullable = false)
    private String protocoloAprovacao;
    @Transient
    //CargaHorariaBasica + CargaHorariaEspecifica
    private BigDecimal cargaHorariaTeorica;
    @Column(name = "cargaHorariaBasica", precision = 7, scale = 2)
    private BigDecimal cargaHorariaBasica;
    @Column(name = "cargaHorariaEspecifica", precision = 7, scale = 2)
    private BigDecimal cargaHorariaEspecifica;
    @Column(name = "cargaHorariaTeoricaInicial", precision = 7, scale = 2)
    private BigDecimal cargaHorariaTeoricaInicial;
    @Column(name = "cargaHorariaPratica", precision = 7, scale = 2)
    private BigDecimal cargaHorariaPratica;
    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VinculoCursoCargo> cargos = new ArrayList<>();

    public Curso() {
    }

    public Curso(LocalDate dataInicio, LocalDate dataFim, Usuario responsavelInicio, Usuario responsavelFim, Integer idCurso, String nomeCurso, String nomePrograma, ModalidadeCurso modalidadeCurso, String protocoloAprovacao, BigDecimal cargaHorariaTeorica, BigDecimal cargaHorariaBasica, BigDecimal cargaHorariaEspecifica, BigDecimal cargaHorariaTeoricaInicial, BigDecimal cargaHorariaPratica) {
        super(dataInicio, dataFim, responsavelInicio, responsavelFim);
        this.idCurso = idCurso;
        this.nomeCurso = nomeCurso;
        this.nomePrograma = nomePrograma;
        this.modalidadeCurso = modalidadeCurso;
        this.protocoloAprovacao = protocoloAprovacao;
        this.cargaHorariaTeorica = cargaHorariaTeorica;
        this.cargaHorariaBasica = cargaHorariaBasica;
        this.cargaHorariaEspecifica = cargaHorariaEspecifica;
        this.cargaHorariaTeoricaInicial = cargaHorariaTeoricaInicial;
        this.cargaHorariaPratica = cargaHorariaPratica;
    }

    public Integer getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Integer idCurso) {
        this.idCurso = idCurso;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = TextoUtils.normalizar(nomeCurso);
    }

    public String getNomePrograma() {
        return nomePrograma;
    }

    public void setNomePrograma(String nomePrograma) {
        this.nomePrograma = TextoUtils.normalizar(nomePrograma);
    }

    public ModalidadeCurso getModalidadeCurso() {
        return modalidadeCurso;
    }

    public void setModalidadeCurso(ModalidadeCurso modalidadeCurso) {
        this.modalidadeCurso = modalidadeCurso;
    }

    public String getProtocoloAprovacao() {
        return protocoloAprovacao;
    }

    public void setProtocoloAprovacao(String protocoloAprovacao) {
        this.protocoloAprovacao = TextoUtils.manterSomenteNumeros(protocoloAprovacao);
    }

    public BigDecimal getCargaHorariaTeorica() {
        BigDecimal basica = this.cargaHorariaBasica != null ? this.cargaHorariaBasica : BigDecimal.ZERO;
        BigDecimal especifica = this.cargaHorariaEspecifica != null ? this.cargaHorariaEspecifica : BigDecimal.ZERO;
        return basica.add(especifica);
    }

    public void setCargaHorariaTeorica(BigDecimal cargaHorariaTeorica) {
        this.cargaHorariaTeorica = cargaHorariaTeorica;
    }

    public BigDecimal getCargaHorariaBasica() {
        return cargaHorariaBasica;
    }

    public void setCargaHorariaBasica(BigDecimal cargaHorariaBasica) {
        this.cargaHorariaBasica = cargaHorariaBasica;
    }

    public BigDecimal getCargaHorariaEspecifica() {
        return cargaHorariaEspecifica;
    }

    public void setCargaHorariaEspecifica(BigDecimal cargaHorariaEspecifica) {
        this.cargaHorariaEspecifica = cargaHorariaEspecifica;
    }

    public BigDecimal getCargaHorariaTeoricaInicial() {
        return cargaHorariaTeoricaInicial;
    }

    public void setCargaHorariaTeoricaInicial(BigDecimal cargaHorariaTeoricaInicial) {
        this.cargaHorariaTeoricaInicial = cargaHorariaTeoricaInicial;
    }

    public BigDecimal getCargaHorariaPratica() {
        return cargaHorariaPratica;
    }

    public void setCargaHorariaPratica(BigDecimal cargaHorariaPratica) {
        this.cargaHorariaPratica = cargaHorariaPratica;
    }

    public List<VinculoCursoCargo> getCargos() {
        return cargos;
    }

    public void setCargos(List<VinculoCursoCargo> cargos) {
        this.cargos = cargos;
    }

    public void adicionarCargo(VinculoCursoCargo cargo) {
        cargos.add(cargo);
        cargo.setCurso(this);
    }

    public void removerCargo(VinculoCursoCargo cargo) {
        cargos.remove(cargo);
        cargo.setCurso(null);
    }
}
