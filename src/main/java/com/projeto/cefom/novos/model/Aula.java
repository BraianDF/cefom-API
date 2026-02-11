package com.projeto.cefom.novos.model;

import com.projeto.cefom.model.Usuario;
import com.projeto.cefom.model.Vigencia;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "aulas")
public class Aula extends Vigencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idAula;
    @Column(name = "horarioInicio", nullable = false)
    private LocalTime horarioInicio;
    @Column(name = "horarioFim", nullable = false)
    private LocalTime horarioFim;
    @ManyToOne
    @JoinColumn(name = "idLecionamento")
    private Lecionamento lecionamento;
    @ManyToOne
    @JoinColumn(name = "idSala")
    private SalaAula sala;
    @ManyToOne
    @JoinColumn(name = "idTurma")
    private Turma turma;
    @OneToMany(mappedBy = "aula", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Presenca> presencas = new ArrayList<>();
    @Transient
    private Boolean chamadaRealizada;

    public Aula() {
    }

    public Aula(LocalDate dataInicio, LocalDate dataFim, Usuario responsavelInicio, Usuario responsavelFim, Integer idAula, LocalTime horarioInicio, LocalTime horarioFim, Lecionamento lecionamento, SalaAula sala, Turma turma, Boolean chamadaRealizada) {
        super(dataInicio, dataFim, responsavelInicio, responsavelFim);
        this.idAula = idAula;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
        this.lecionamento = lecionamento;
        this.sala = sala;
        this.turma = turma;
        this.chamadaRealizada = chamadaRealizada;
    }

    public Integer getIdAula() {
        return idAula;
    }

    public void setIdAula(Integer idAula) {
        this.idAula = idAula;
    }

    public LocalTime getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(LocalTime horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public LocalTime getHorarioFim() {
        return horarioFim;
    }

    public void setHorarioFim(LocalTime horarioFim) {
        this.horarioFim = horarioFim;
    }

    public Lecionamento getLecionamento() {
        return lecionamento;
    }

    public void setLecionamento(Lecionamento lecionamento) {
        this.lecionamento = lecionamento;
    }

    public SalaAula getSala() {
        return sala;
    }

    public void setSala(SalaAula sala) {
        this.sala = sala;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public List<Presenca> getPresencas() {
        return presencas;
    }

    public void setPresencas(List<Presenca> presencas) {
        this.presencas = presencas;
    }

    public Boolean getChamadaRealizada() {
        return presencas != null && !presencas.isEmpty();
    }

    public void setChamadaRealizada(Boolean chamadaRealizada) {
        this.chamadaRealizada = chamadaRealizada;
    }

    public void adicionarPresenca(Presenca presenca) {
        presencas.add(presenca);
        presenca.setAula(this);
    }

    public void removerPresenca(Presenca presenca) {
        presencas.remove(presenca);
        presenca.setAula(null);
    }
}
