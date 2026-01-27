package com.projeto.sistema.model;

import com.projeto.sistema.enums.TipoEscola;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "escolas")
public class Escola implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idEscola;
    @Column(name = "nome", length = 50, nullable = false, unique = true)
    private String nome;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoEscola tipo;
    @OneToMany(mappedBy = "escola", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Endereco> enderecos = new ArrayList<>();
    @OneToMany(mappedBy = "escola", cascade = CascadeType.ALL)
    private List<Escolaridade> escolaridades = new ArrayList<>();

    public Escola() {
    }

    public Escola(Integer idEscola, String nome, TipoEscola tipo) {
        this.idEscola = idEscola;
        this.nome = nome;
        this.tipo = tipo;
    }

    public Integer getIdEscola() {
        return idEscola;
    }

    public void setIdEscola(Integer idEscola) {
        this.idEscola = idEscola;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoEscola getTipo() {
        return tipo;
    }

    public void setTipo(TipoEscola tipo) {
        this.tipo = tipo;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public List<Escolaridade> getEscolaridades() {
        return escolaridades;
    }

    public void setEscolaridades(List<Escolaridade> escolaridades) {
        this.escolaridades = escolaridades;
    }

    public void adicionarEscolaridade(Escolaridade escolaridade) {
        escolaridades.add(escolaridade);
        escolaridade.setEscola(this);
    }

    public void removerEscolaridade(Escolaridade escolaridade) {
        escolaridades.remove(escolaridade);
        escolaridade.setEscola(null);
    }

    public void adicionarEndereco(Endereco endereco) {
        enderecos.add(endereco);
        endereco.setEscola(this);
    }

    public void removerEndereco(Endereco endereco) {
        enderecos.remove(endereco);
        endereco.setEscola(null);
    }
}
