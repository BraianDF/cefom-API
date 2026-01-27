package com.projeto.sistema.mapper;

import com.projeto.sistema.dto.response.EnderecoListarResponseDTO;
import com.projeto.sistema.dto.response.EnderecoMatriculaResponseDTO;
import com.projeto.sistema.dto.response.EnderecoResponseDTO;
import com.projeto.sistema.model.Adolescente;
import com.projeto.sistema.model.Endereco;
import com.projeto.sistema.model.Escola;
import com.projeto.sistema.model.Empresa;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.Comparator;

@Component
public class EnderecoMapper {

    public EnderecoMatriculaResponseDTO toResponseDTO(Adolescente adolescente, LocalDate data) {
        Endereco endereco = adolescente.getEnderecos()
                .stream()
                .filter(e -> e.estaValidoEm(data))
                .max(Comparator.comparing(Endereco::getDataInicio))
                .orElse(null);

        return toAdolescenteResponseDTO(endereco);
    }

    public EnderecoMatriculaResponseDTO toAdolescenteResponseDTO(Endereco endereco) {
        return new EnderecoMatriculaResponseDTO(
                endereco != null ? endereco.getIdEndereco() : null,
                endereco != null ? endereco.getCep() : null,
                endereco != null ? endereco.getLogradouro() : null,
                endereco != null ? endereco.getNumero() : null,
                endereco != null ? endereco.getComplemento() : null,
                endereco != null ? endereco.getBairro() : null,
                endereco != null ? endereco.getCidade() : null,
                endereco != null ? endereco.getEstado() : null,
                endereco != null ? endereco.getTipoResidencia() : null,
                endereco != null ? endereco.getTerritorio().getResultado() : null,
                endereco != null ? endereco.getDataInicio() : null,
                endereco != null ? endereco.getDataFim() : null
        );
    }

    public EnderecoResponseDTO toInscricaoResponseDTO(Adolescente adolescente, LocalDate data) {
        Endereco endereco = adolescente.getEnderecos()
                .stream()
                .filter(e -> e.estaValidoEm(data))
                .max(Comparator.comparing(Endereco::getDataInicio))
                .orElse(null);

        return toResponseDTO(endereco);
    }

    public EnderecoResponseDTO toResponseDTO(Escola escola, LocalDate data) {
        Endereco endereco = escola.getEnderecos()
                .stream()
                .filter(e -> e.estaValidoEm(data))
                .max(Comparator.comparing(Endereco::getDataInicio))
                .orElse(null);

        return toResponseDTO(endereco);
    }

    public EnderecoResponseDTO toResponseDTO(Empresa empresa, LocalDate data) {
        Endereco endereco = empresa.getEnderecos()
                .stream()
                .filter(e -> e.estaValidoEm(data))
                .max(Comparator.comparing(Endereco::getDataInicio))
                .orElse(null);

        return toResponseDTO(endereco);
    }

    public EnderecoResponseDTO toResponseDTO(Endereco endereco) {
        return new EnderecoResponseDTO(
                endereco != null ? endereco.getIdEndereco() : null,
                endereco != null ? endereco.getCep() : null,
                endereco != null ? endereco.getLogradouro() : null,
                endereco != null ? endereco.getNumero() : null,
                endereco != null ? endereco.getComplemento() : null,
                endereco != null ? endereco.getBairro() : null,
                endereco != null ? endereco.getCidade() : null,
                endereco != null ? endereco.getEstado() : null,
                endereco != null ? endereco.getTerritorio().getResultado() : null,
                endereco != null ? endereco.getDataInicio() : null,
                endereco != null ? endereco.getDataFim() : null
        );
    }

    public EnderecoListarResponseDTO toListarResponseDTO(Endereco endereco) {
        return new EnderecoListarResponseDTO(
                endereco != null ? endereco.getIdEndereco() : null,
                endereco != null ? endereco.getDataInicio() : null,
                endereco != null ? endereco.getDataFim() : null,
                endereco != null ? endereco.getTerritorio().getResultado() : null
        );
    }
}
