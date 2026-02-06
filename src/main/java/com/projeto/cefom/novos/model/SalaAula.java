package com.projeto.cefom.novos.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "salasAulas")
public class SalaAula implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idSalaAula;
    @Column(name = "nome", length = 50, nullable = false)
    private String nome;
    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Aula> aulas = new ArrayList<>();

    public SalaAula() {
    }

    public SalaAula(Integer idSalaAula, String nome) {
        this.idSalaAula = idSalaAula;
        this.nome = nome;
    }

    public Integer getIdSalaAula() {
        return idSalaAula;
    }

    public void setIdSalaAula(Integer idSalaAula) {
        this.idSalaAula = idSalaAula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Aula> getAulas() {
        return aulas;
    }

    public void setAulas(List<Aula> aulas) {
        this.aulas = aulas;
    }
}
