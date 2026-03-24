package com.projeto.cefom.model;

import com.projeto.cefom.exceptions.RegraNegocioException;
import com.projeto.cefom.utils.TextoUtils;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "territorios")
public class Territorio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idTerritorio;
    @Column(name = "resultado", length = 50, nullable = false)
    private String resultado;
    @ElementCollection
    @CollectionTable(name = "territorioBairros", joinColumns = @JoinColumn(name = "idTerritorio"), uniqueConstraints = { @UniqueConstraint(columnNames = {"idTerritorio", "bairro"})})
    @Column(name = "bairro")
    private Set<String> bairros = new HashSet<>();
    @OneToMany(mappedBy = "territorio")
    private List<Endereco> enderecos = new ArrayList<>();

    public Territorio() {
    }

    public Territorio(Integer idTerritorio, String resultado) {
        this.idTerritorio = idTerritorio;
        this.resultado = resultado;
    }

    public Integer getIdTerritorio() {
        return idTerritorio;
    }

    public void setIdTerritorio(Integer idTerritorio) {
        this.idTerritorio = idTerritorio;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = TextoUtils.normalizar(resultado);
    }

    public Set<String> getBairros() {
        return bairros;
    }

    public void setBairros(Set<String> bairros) {
        this.bairros = bairros;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public void adicionarEndereco(Endereco endereco) {
        enderecos.add(endereco);
        endereco.setTerritorio(this);
    }

    public void removerEndereco(Endereco endereco) {
        enderecos.remove(endereco);
        endereco.setTerritorio(null);
    }

    public void adicionarBairro(String bairro) {
        if (bairro == null || bairro.isBlank()) {
            throw new RegraNegocioException("Bairro inválido.");
        }

        boolean jaExiste = bairros.stream()
                .anyMatch(b -> TextoUtils.equalsNormalizado(b, bairro));

        if (jaExiste) {
            throw new RegraNegocioException("O bairro '" + bairro + "' já está cadastrado neste território.");
        }

        bairros.add(TextoUtils.normalizar(bairro));
    }

    public void removerBairro(String bairro) {
        if (bairro == null || bairro.isBlank()) {
            throw new RegraNegocioException("Bairro inválido.");
        }

        boolean jaExiste = bairros.stream()
                .anyMatch(b -> TextoUtils.equalsNormalizado(b, bairro));

        if (!jaExiste) {
            throw new RegraNegocioException("O bairro '" + bairro + "' não está cadastrado neste território.");
        }

        bairros.remove(TextoUtils.normalizar(bairro));
    }
}
