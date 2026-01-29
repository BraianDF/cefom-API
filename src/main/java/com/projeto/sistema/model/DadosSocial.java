package com.projeto.sistema.model;

import com.projeto.sistema.enums.Beneficio;
import com.projeto.sistema.enums.Parentesco;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Table(name = "dadosSociais")
public class DadosSocial extends Vigencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idDadosSocial;
    @Column(name = "comportamentoBoolean")
    private Boolean comportamentoBoolean;
    @Column(name = "comportamento", length = 50)
    private String comportamento;
    @Column(name = "encaminhamentoBoolean")
    private Boolean encaminhamentoBoolean;
    @Column(name = "encaminhamento", length = 50)
    private String encaminhamento;
    @Column(name = "beneficioBoolean")
    private Boolean beneficioBoolean;
    @Enumerated(EnumType.STRING)
    @Column(name = "beneficio")
    private Beneficio beneficio;
    @Column(name = "beneficioValor", precision = 8, scale = 2)
    private BigDecimal beneficioValor;
    @Column(name = "problemaSaudeBoolean")
    private Boolean problemaSaudeBoolean;
    @Column(name = "nome", length = 50)
    private String problemaSaude;
    @Column(name = "medicamentoBoolean")
    private Boolean medicamentoBoolean;
    @Column(name = "medicamento", length = 50)
    private String medicamento;
    @Column(name = "entidadeBoolean")
    private Boolean entidadeBoolean;
    @Column(name = "entidade", length = 50)
    private String entidade;
    @Transient
    private BigDecimal rendaFamiliar;
    @Transient
    private String composicaoFamiliar;
    @ManyToOne
    @JoinColumn(name = "idAdolescente")
    private Adolescente adolescente;

    public DadosSocial() {
    }

    public DadosSocial(LocalDate dataInicio, LocalDate dataFim, Usuario responsavelInicio, Usuario responsavelFim, Integer idDadosSocial, Boolean comportamentoBoolean, String comportamento, Boolean encaminhamentoBoolean, String encaminhamento, Boolean beneficioBoolean, Beneficio beneficio, BigDecimal beneficioValor, Boolean problemaSaudeBoolean, String problemaSaude, Boolean medicamentoBoolean, String medicamento, Boolean entidadeBoolean, String entidade, BigDecimal rendaFamiliar, String composicaoFamiliar, Adolescente adolescente) {
        super(dataInicio, dataFim, responsavelInicio, responsavelFim);
        this.idDadosSocial = idDadosSocial;
        this.comportamentoBoolean = comportamentoBoolean;
        this.comportamento = comportamento;
        this.encaminhamentoBoolean = encaminhamentoBoolean;
        this.encaminhamento = encaminhamento;
        this.beneficioBoolean = beneficioBoolean;
        this.beneficio = beneficio;
        this.beneficioValor = beneficioValor;
        this.problemaSaudeBoolean = problemaSaudeBoolean;
        this.problemaSaude = problemaSaude;
        this.medicamentoBoolean = medicamentoBoolean;
        this.medicamento = medicamento;
        this.entidadeBoolean = entidadeBoolean;
        this.entidade = entidade;
        this.rendaFamiliar = rendaFamiliar;
        this.composicaoFamiliar = composicaoFamiliar;
        this.adolescente = adolescente;
    }

    public Integer getIdDadosSocial() {
        return idDadosSocial;
    }

    public void setIdDadosSocial(Integer idDadosSocial) {
        this.idDadosSocial = idDadosSocial;
    }

    public Boolean getComportamentoBoolean() {
        return comportamentoBoolean;
    }

    public void setComportamentoBoolean(Boolean comportamentoBoolean) {
        this.comportamentoBoolean = comportamentoBoolean;
    }

    public String getComportamento() {
        return comportamento;
    }

    public void setComportamento(String comportamento) {
        this.comportamento = comportamento;
    }

    public Boolean getEncaminhamentoBoolean() {
        return encaminhamentoBoolean;
    }

    public void setEncaminhamentoBoolean(Boolean encaminhamentoBoolean) {
        this.encaminhamentoBoolean = encaminhamentoBoolean;
    }

    public String getEncaminhamento() {
        return encaminhamento;
    }

    public void setEncaminhamento(String encaminhamento) {
        this.encaminhamento = encaminhamento;
    }

    public Boolean getBeneficioBoolean() {
        return beneficioBoolean;
    }

    public void setBeneficioBoolean(Boolean beneficioBoolean) {
        this.beneficioBoolean = beneficioBoolean;
    }

    public Beneficio getBeneficio() {
        return beneficio;
    }

    public void setBeneficio(Beneficio beneficio) {
        this.beneficio = beneficio;
    }

    public BigDecimal getBeneficioValor() {
        return beneficioValor;
    }

    public void setBeneficioValor(BigDecimal beneficioValor) {
        this.beneficioValor = beneficioValor;
    }

    public Boolean getProblemaSaudeBoolean() {
        return problemaSaudeBoolean;
    }

    public void setProblemaSaudeBoolean(Boolean problemaSaudeBoolean) {
        this.problemaSaudeBoolean = problemaSaudeBoolean;
    }

    public String getProblemaSaude() {
        return problemaSaude;
    }

    public void setProblemaSaude(String problemaSaude) {
        this.problemaSaude = problemaSaude;
    }

    public Boolean getMedicamentoBoolean() {
        return medicamentoBoolean;
    }

    public void setMedicamentoBoolean(Boolean medicamentoBoolean) {
        this.medicamentoBoolean = medicamentoBoolean;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public Boolean getEntidadeBoolean() {
        return entidadeBoolean;
    }

    public void setEntidadeBoolean(Boolean entidadeBoolean) {
        this.entidadeBoolean = entidadeBoolean;
    }

    public String getEntidade() {
        return entidade;
    }

    public void setEntidade(String entidade) {
        this.entidade = entidade;
    }

    public BigDecimal getRendaFamiliar() {
        return getRendaFamiliarEm(LocalDate.now());
    }

    public BigDecimal getRendaFamiliarEm(LocalDate data) {
        BigDecimal renda = adolescente.getFamiliares().stream()
                .filter(f -> f.estaValidoEm(data))
                .filter(f -> !f.isResponsavel() || Boolean.TRUE.equals(f.getReside()))
                .map(Familiar::getRenda) // pega a renda do familiar
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        renda = renda.add(getBeneficioValor() != null ? getBeneficioValor() : BigDecimal.ZERO);

        return renda;
    }

    public void setRendaFamiliar(BigDecimal rendaFamiliar) {
        this.rendaFamiliar = rendaFamiliar;
    }

    public String getComposicaoFamiliar() {
        return getComposicaoFamiliarEm(LocalDate.now());
    }

    public String getComposicaoFamiliarEm(LocalDate data) {
        Map<Parentesco, Long> agrupamento = Optional.ofNullable(adolescente.getFamiliares())
                .orElse(Collections.emptyList())
                .stream()
                .filter(f -> f.estaValidoEm(data))
                .filter(f -> !f.isResponsavel() || Boolean.TRUE.equals(f.getReside()))
                .collect(Collectors.groupingBy(Familiar::getParentesco, Collectors.counting()));

        if (agrupamento.isEmpty()) {
            return "Adolescente";
        }

        String familiares = agrupamento.entrySet().stream()
                .map(entry -> {
                    long count = entry.getValue();
                    Parentesco parentesco = entry.getKey();

                    String descricao = parentesco.getDescricao();
                    if (count > 1) {
                        descricao = pluralizar(descricao);
                        return count + " " + descricao;
                    } else {
                        return descricao;
                    }
                })
                .collect(Collectors.joining(", "));

        return "Adolescente + " + familiares;
    }

    private String pluralizar(String palavra) {
        // Plural simples para português - pode melhorar se quiser regras mais complexas
        if (palavra.endsWith("ão")) {
            // Exemplo: "irmão" -> "irmãos"
            return palavra.substring(0, palavra.length() - 2) + "ões";
        } else if (palavra.endsWith("m")) {
            // Exemplo: "jardim" -> "jardins"
            return palavra + "s";
        } else if (palavra.endsWith("r") || palavra.endsWith("z")) {
            // Exemplo: "flor" -> "flores"
            return palavra + "es";
        } else if (palavra.endsWith("l")) {
            // Exemplo: "animal" -> "animais"
            return palavra.substring(0, palavra.length() - 1) + "ais";
        } else if (palavra.endsWith("a") || palavra.endsWith("e") || palavra.endsWith("i") || palavra.endsWith("o") || palavra.endsWith("u")) {
            // Exemplo: "tia" -> "tias"
            return palavra + "s";
        } else {
            // Plural padrão adicionando "s"
            return palavra + "s";
        }
    }

    public void setComposicaoFamiliar(String composicaoFamiliar) {
        this.composicaoFamiliar = composicaoFamiliar;
    }

    public Adolescente getAdolescente() {
        return adolescente;
    }

    public void setAdolescente(Adolescente adolescente) {
        this.adolescente = adolescente;
    }
}
