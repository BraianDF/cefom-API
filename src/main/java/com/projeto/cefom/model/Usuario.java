package com.projeto.cefom.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idUsuario;
    @Column(name = "nome", length = 50, nullable = false)
    private String nome;
    @Column(name = "usuario", length = 20, nullable = false)
    private String usuario;
    @Column(name = "senha", length = 50, nullable = false)
    private String senha;
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @OneToMany(mappedBy = "responsavelInicio", cascade = CascadeType.ALL)
    private List<Caracteristica> caracteristicasCriadas = new ArrayList<>();
    @OneToMany(mappedBy = "responsavelFim", cascade = CascadeType.ALL)
    private List<Caracteristica> caracteristicasEncerradas = new ArrayList<>();
    @OneToMany(mappedBy = "responsavelInicio", cascade = CascadeType.ALL)
    private List<Contrato> contratosCriados = new ArrayList<>();
    @OneToMany(mappedBy = "responsavelFim", cascade = CascadeType.ALL)
    private List<Contrato> contratosEncerrados = new ArrayList<>();
    @OneToMany(mappedBy = "responsavelInicio", cascade = CascadeType.ALL)
    private List<DadosSocial> dadosSociaisCriados = new ArrayList<>();
    @OneToMany(mappedBy = "responsavelFim", cascade = CascadeType.ALL)
    private List<DadosSocial> dadosSociaisEncerrados = new ArrayList<>();
    @OneToMany(mappedBy = "responsavelInicio", cascade = CascadeType.ALL)
    private List<Email> emailsCriados = new ArrayList<>();
    @OneToMany(mappedBy = "responsavelFim", cascade = CascadeType.ALL)
    private List<Email> emailsEncerrados = new ArrayList<>();
    @OneToMany(mappedBy = "responsavelInicio", cascade = CascadeType.ALL)
    private List<Endereco> enderecosCriados = new ArrayList<>();
    @OneToMany(mappedBy = "responsavelFim", cascade = CascadeType.ALL)
    private List<Endereco> enderecosEncerrados = new ArrayList<>();
    @OneToMany(mappedBy = "responsavelInicio", cascade = CascadeType.ALL)
    private List<Escolaridade> escolaridadesCriadas = new ArrayList<>();
    @OneToMany(mappedBy = "responsavelFim", cascade = CascadeType.ALL)
    private List<Escolaridade> escolaridadesEncerradas = new ArrayList<>();
    @OneToMany(mappedBy = "responsavelInicio", cascade = CascadeType.ALL)
    private List<Familiar> familiaresCriados = new ArrayList<>();
    @OneToMany(mappedBy = "responsavelFim", cascade = CascadeType.ALL)
    private List<Familiar> familiaresEncerrados = new ArrayList<>();
    @OneToMany(mappedBy = "responsavelInicio", cascade = CascadeType.ALL)
    private List<Inscricao> inscricoesCriadas = new ArrayList<>();
    @OneToMany(mappedBy = "responsavelFim", cascade = CascadeType.ALL)
    private List<Inscricao> inscricoesEncerradas = new ArrayList<>();
    @OneToMany(mappedBy = "responsavelInicio", cascade = CascadeType.ALL)
    private List<Matricula> matriculasCriadas = new ArrayList<>();
    @OneToMany(mappedBy = "responsavelFim", cascade = CascadeType.ALL)
    private List<Matricula> matriculasEncerradas = new ArrayList<>();
    @OneToMany(mappedBy = "responsavelInicio", cascade = CascadeType.ALL)
    private List<Telefone> telefonesCriados = new ArrayList<>();
    @OneToMany(mappedBy = "responsavelFim", cascade = CascadeType.ALL)
    private List<Telefone> telefonesEncerrados = new ArrayList<>();


    public Usuario() {
    }

    public Usuario(Integer idUsuario, String nome, String usuario, String senha, Boolean ativo) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.usuario = usuario;
        this.senha = senha;
        this.ativo = ativo;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
