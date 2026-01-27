package com.projeto.sistema.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "SequenceNumInscricao")
public class SequenceNumInscricao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "nome", length = 50)
    private String nome;

    @Column(name = "valor", nullable = false)
    private Integer valor;

    public SequenceNumInscricao() {
    }

    public SequenceNumInscricao(String nome, Integer valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }
}
