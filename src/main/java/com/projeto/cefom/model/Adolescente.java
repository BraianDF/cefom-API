package com.projeto.cefom.model;

import com.projeto.cefom.enums.*;
import com.projeto.cefom.utils.TextoUtils;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "adolescentes")
public class Adolescente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idAdolescente;
    @Column(name = "nome", length = 50, nullable = false)
    private String nome;
    @Enumerated(EnumType.STRING)
    @Column(name = "genero", nullable = false)
    private Genero genero;
    @Column(name = "dataNascimento", nullable = false)
    private LocalDate dataNascimento;
    @Column(name = "cidadeNascimento", length = 50)
    private String cidadeNascimento;
    @Enumerated(EnumType.STRING)
    @Column(name = "estadoNascimento", length = 50)
    private Estado estadoNascimento;
    @Column(name = "paisNascimento", length = 50)
    private String paisNascimento;
    @Enumerated(EnumType.STRING)
    @Column(name = "naturalidade")
    private Naturalidade naturalidade;
    @Enumerated(EnumType.STRING)
    @Column(name = "estadoCivil")
    private EstadoCivil estadoCivil;
    @Column(name = "mae", length = 50)
    private String mae;
    @Column(name = "pai", length = 50)
    private String pai;
    @Column(name = "conjuge", length = 50)
    private String conjuge;
    @Transient
    private Situacao situacao;
    @Transient
    private Integer idade;
    @OneToOne(mappedBy = "adolescente", cascade = CascadeType.ALL, orphanRemoval = true)
    private Documento documento;
    @OneToMany(mappedBy = "adolescente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DadosSocial> dadosSociais = new ArrayList<>();
    @OneToMany(mappedBy = "adolescente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Caracteristica> caracteristicas = new ArrayList<>();
    @OneToMany(mappedBy = "adolescente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Endereco> enderecos = new ArrayList<>();
    @OneToMany(mappedBy = "adolescente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Email> emails = new ArrayList<>();
    @OneToMany(mappedBy = "adolescente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Telefone> telefones = new ArrayList<>();
    @OneToMany(mappedBy = "adolescente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Escolaridade> escolaridades = new ArrayList<>();
    @OneToMany(mappedBy = "adolescente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Matricula> matriculas = new ArrayList<>();

    @OneToMany(mappedBy = "adolescente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inscricao> inscricoes = new ArrayList<>();

    @OneToMany(mappedBy = "adolescente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Familiar> familiares = new ArrayList<>();

    public Adolescente() {
    }

    public Adolescente(Integer idAdolescente, String nome, Genero genero, LocalDate dataNascimento, String cidadeNascimento, Estado estadoNascimento, String paisNascimento, Naturalidade naturalidade, EstadoCivil estadoCivil, String mae, String pai, String conjuge, Situacao situacao, Integer idade, Documento documento) {
        this.idAdolescente = idAdolescente;
        this.nome = nome;
        this.genero = genero;
        this.dataNascimento = dataNascimento;
        this.cidadeNascimento = cidadeNascimento;
        this.estadoNascimento = estadoNascimento;
        this.paisNascimento = paisNascimento;
        this.naturalidade = naturalidade;
        this.estadoCivil = estadoCivil;
        this.mae = mae;
        this.pai = pai;
        this.conjuge = conjuge;
        this.situacao = situacao;
        this.idade = idade;
        this.documento = documento;
    }

    public Integer getIdAdolescente() {
        return idAdolescente;
    }

    public void setIdAdolescente(Integer idAdolescente) {
        this.idAdolescente = idAdolescente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = TextoUtils.normalizar(nome);;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCidadeNascimento() {
        return cidadeNascimento;
    }

    public void setCidadeNascimento(String cidadeNascimento) {
        this.cidadeNascimento = TextoUtils.normalizar(cidadeNascimento);
    }

    public Estado getEstadoNascimento() {
        return estadoNascimento;
    }

    public void setEstadoNascimento(Estado estadoNascimento) {
        this.estadoNascimento = estadoNascimento;
    }

    public String getPaisNascimento() {
        return paisNascimento;
    }

    public void setPaisNascimento(String paisNascimento) {
        this.paisNascimento = TextoUtils.normalizar(paisNascimento);
    }

    public Naturalidade getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(Naturalidade naturalidade) {
        this.naturalidade = naturalidade;
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getMae() {
        return mae;
    }

    public void setMae(String mae) {
        this.mae = TextoUtils.normalizar(mae);
    }

    public String getPai() {
        return pai;
    }

    public void setPai(String pai) {
        this.pai = TextoUtils.normalizar(pai);
    }

    public String getConjuge() {
        return conjuge;
    }

    public void setConjuge(String conjuge) {
        this.conjuge = TextoUtils.normalizar(conjuge);
    }

    public Situacao getSituacao() {
        return getSituacaoEm(LocalDate.now());
    }

    public Situacao getSituacaoEm(LocalDate data) {
        if (isNaoIniciadoEm(data)) {
            return Situacao.NAO_INICIADO;
        }
        if (isEstagiandoEm(data)) {
            return Situacao.ESTAGIANDO;
        }
        if (isMatriculadoEmEsperaEm(data)) {
            return Situacao.MATRICULADO_EM_ESPERA;
        }
        if (isMatriculadoEm(data)) {
            return Situacao.MATRICULADO;
        }
        if (isInscritoEm(data)) {
            return Situacao.INSCRITO;
        }
        if (isInscritoInativoEm(data)) {
            return Situacao.INSCRITO_INATIVO;
        }
        if (isDesligadoEm(data)) {
            return Situacao.DESLIGADO;
        }
        throw new IllegalStateException("Situação não mapeada para a data " + data+".");
    }

    private boolean isNaoIniciadoEm(LocalDate data) {
        boolean algumaInscricaoJaIniciou = inscricoes.stream()
                .anyMatch(i -> i.getDataInicio() != null && !i.getDataInicio().isAfter(data));

        boolean algumaMatriculaJaIniciou = matriculas.stream()
                .anyMatch(m -> m.getDataInicio() != null && !m.getDataInicio().isAfter(data));

        return !(algumaInscricaoJaIniciou || algumaMatriculaJaIniciou);
    }

    private boolean isEstagiandoEm(LocalDate data) {
        return matriculas.stream().anyMatch(m ->
                // matrícula ativa na data
                m.estaValidoEm(data) &&
                // existe contrato dessa matrícula ativo na data e já iniciado
                m.getContratos().stream().anyMatch(c -> c.estaValidoEm(data))
        );
    }

    private boolean isMatriculadoEmEsperaEm(LocalDate data) {
        return matriculas.stream().anyMatch(m ->
                // matrícula ativa na data
                m.estaValidoEm(data) &&
                m.getSituacaoMatricula() == SituacaoMatricula.ENCAMINHADO &&
                m.getContratos().stream().noneMatch(c -> c.estaValidoEm(data))
        );
    }

    private boolean isMatriculadoEm(LocalDate data) {
        return matriculas.stream().anyMatch(m ->
                // matrícula ativa na data
                m.estaValidoEm(data) &&
                m.getSituacaoMatricula() != SituacaoMatricula.ENCAMINHADO &&
                m.getContratos().stream().noneMatch(c -> c.estaValidoEm(data))
        );
    }

    private boolean isInscritoEm(LocalDate data) {
        boolean inscricaoAtiva = inscricoes.stream()
                .anyMatch(i -> i.estaValidoEm(data));

        boolean nenhumaMatriculaAtiva = matriculas.stream()
                .noneMatch(m -> m.estaValidoEm(data));

        return inscricaoAtiva && nenhumaMatriculaAtiva;
    }

    private boolean isInscritoInativoEm(LocalDate data) {
        boolean existeAlgumaInscricao = !inscricoes.isEmpty();

        boolean todasInscricoesJaEncerraram = existeAlgumaInscricao && inscricoes.stream()
                .allMatch(i -> i.getDataFim() != null && !i.getDataFim().isAfter(data));

        boolean algumaMatriculaJaIniciou = matriculas.stream()
                .anyMatch(m -> m.getDataInicio() != null && !m.getDataInicio().isAfter(data));

        return (todasInscricoesJaEncerraram && !algumaMatriculaJaIniciou);
    }

    private boolean isDesligadoEm(LocalDate data) {
        boolean algumaMatriculaJaIniciou = matriculas.stream()
                .anyMatch(m -> m.getDataInicio() != null && !m.getDataInicio().isAfter(data));

        boolean inscricaoAtiva = inscricoes.stream()
                .anyMatch(i -> i.estaValidoEm(data));

        boolean matriculaAtiva = matriculas.stream()
                .anyMatch(m -> m.estaValidoEm(data));

        return algumaMatriculaJaIniciou && !inscricaoAtiva && !matriculaAtiva;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public Integer getIdade() {
        return getIdadeEm(LocalDate.now());
    }

    public Integer getIdadeEm(LocalDate data) {
        if (dataNascimento == null) {
            throw new IllegalStateException("Data de nascimento não informada");
        }
        if (data.isBefore(dataNascimento)) {
            throw new IllegalArgumentException("Data de referência anterior à data de nascimento");
        }
        return Period.between(dataNascimento, data).getYears();
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public List<DadosSocial> getDadosSociais() {
        return dadosSociais;
    }

    public void setDadosSociais(List<DadosSocial> dadosSociais) {
        this.dadosSociais = dadosSociais;
    }

    public List<Caracteristica> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(List<Caracteristica> caracteristicas) {
        this.caracteristicas = caracteristicas;
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

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }

    public List<Escolaridade> getEscolaridades() {
        return escolaridades;
    }

    public void setEscolaridades(List<Escolaridade> escolaridades) {
        this.escolaridades = escolaridades;
    }

    public List<Inscricao> getInscricoes() {
        return inscricoes;
    }

    public void setInscricoes(List<Inscricao> inscricoes) {
        this.inscricoes = inscricoes;
    }

    public List<Familiar> getFamiliares() {
        return familiares;
    }

    public void setFamiliares(List<Familiar> familiares) {
        this.familiares = familiares;
    }

    public void adicionarMatricula(Matricula matricula) {
        matriculas.add(matricula);
        matricula.setAdolescente(this);
    }

    public void removerMatricula(Matricula matricula) {
        matriculas.remove(matricula);
        matricula.setAdolescente(null);
    }

    public void adicionarInscricao(Inscricao inscricao) {
        inscricoes.add(inscricao);
        inscricao.setAdolescente(this);
    }

    public void removerInscricao(Inscricao inscricao) {
        inscricoes.remove(inscricao);
        inscricao.setAdolescente(null);
    }

    public void adicionarEndereco(Endereco endereco) {
        enderecos.add(endereco);
        endereco.setAdolescente(this);
    }

    public void removerEndereco(Endereco endereco) {
        enderecos.remove(endereco);
        endereco.setAdolescente(null);
    }

    public void adicionarEmail(Email email) {
        emails.add(email);
        email.setAdolescente(this);
    }

    public void removerEmail(Email email) {
        emails.remove(email);
        email.setAdolescente(null);
    }

    public void adicionarTelefone(Telefone telefone) {
        telefones.add(telefone);
        telefone.setAdolescente(this);
    }

    public void removerTelefone(Telefone telefone) {
        telefones.remove(telefone);
        telefone.setAdolescente(null);
    }

    public void adicionarEscolaridade(Escolaridade escolaridade) {
        escolaridades.add(escolaridade);
        escolaridade.setAdolescente(this);
    }

    public void removerEscolaridade(Escolaridade escolaridade) {
        escolaridades.remove(escolaridade);
        escolaridade.setAdolescente(null);
    }

    public void adicionarFamiliar(Familiar familiar) {
        familiares.add(familiar);
        familiar.setAdolescente(this);
    }

    public void removerFamiliar(Familiar familiar) {
        familiares.remove(familiar);
        familiar.setAdolescente(null);
    }

    public void adicionarDadosSocial(DadosSocial dadosSocial) {
        dadosSociais.add(dadosSocial);
        dadosSocial.setAdolescente(this);
    }

    public void removerDadosSocial(DadosSocial dadosSocial) {
        dadosSociais.remove(dadosSocial);
        dadosSocial.setAdolescente(null);
    }

    public void adicionarCaracteristica(Caracteristica caracteristica) {
        caracteristicas.add(caracteristica);
        caracteristica.setAdolescente(this);
    }

    public void removerFamiliar(Caracteristica caracteristica) {
        caracteristicas.remove(caracteristica);
        caracteristica.setAdolescente(null);
    }

    public void adicionarDocumento(Documento documento) {
        this.setDocumento(documento);
        documento.setAdolescente(this);
    }

    public void removerDocumento() {
        if (this.documento != null) {
            this.documento.setAdolescente(null);
            this.documento = null;
        }
    }
}
