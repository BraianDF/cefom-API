package com.projeto.cefom.image.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;

@MappedSuperclass
public abstract class Arquivo {
    @Column(name="nomeArquivo", length = 150, nullable = false)
    private String nomeArquivo; //UUID
    @Column(name="tipoArquivo", length = 50, nullable = false)
    private String tipoArquivo;
    @Column(name="tamanhoArquivo", nullable = false)
    private long tamanhoArquivo;
    @Transient
    private String caminhoArquivo;

    public Arquivo() {
    }

    public Arquivo(String nomeArquivo, String tipoArquivo, long tamanhoArquivo, String caminhoArquivo) {
        this.nomeArquivo = nomeArquivo;
        this.tipoArquivo = tipoArquivo;
        this.tamanhoArquivo = tamanhoArquivo;
        this.caminhoArquivo = caminhoArquivo;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getTipoArquivo() {
        return tipoArquivo;
    }

    public void setTipoArquivo(String tipoArquivo) {
        this.tipoArquivo = tipoArquivo;
    }

    public long getTamanhoArquivo() {
        return tamanhoArquivo;
    }

    public void setTamanhoArquivo(long tamanhoArquivo) {
        this.tamanhoArquivo = tamanhoArquivo;
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }
}
