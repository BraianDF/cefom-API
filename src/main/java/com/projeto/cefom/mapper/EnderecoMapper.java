package com.projeto.cefom.mapper;

import com.projeto.cefom.dto.response.EnderecoListarResponseDTO;
import com.projeto.cefom.dto.response.EnderecoMatriculaResponseDTO;
import com.projeto.cefom.dto.response.EnderecoResponseDTO;
import com.projeto.cefom.model.Adolescente;
import com.projeto.cefom.model.Endereco;
import com.projeto.cefom.model.Escola;
import com.projeto.cefom.model.Empresa;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.Comparator;

@Component
public class EnderecoMapper {

    public EnderecoMatriculaResponseDTO toResponseDTO(Adolescente adolescente, LocalDate data) {
        if (adolescente == null) return null;

        Endereco endereco = adolescente.getEnderecos()
                .stream()
                .filter(e -> e.estaValidoEm(data))
                .max(Comparator.comparing(Endereco::getDataInicio))
                .orElse(null);

        return toAdolescenteResponseDTO(endereco);
    }

    public EnderecoMatriculaResponseDTO toAdolescenteResponseDTO(Endereco endereco) {
        if (endereco == null) return null;

        return new EnderecoMatriculaResponseDTO(
                endereco.getIdEndereco(),
                endereco.getCep(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getTipoResidencia(),
                endereco.getTerritorio().getResultado(),
                endereco.getDataInicio(),
                endereco.getDataFim()
        );
    }

    public EnderecoResponseDTO toInscricaoResponseDTO(Adolescente adolescente, LocalDate data) {
        if (adolescente == null) return null;

        Endereco endereco = adolescente.getEnderecos()
                .stream()
                .filter(e -> e.estaValidoEm(data))
                .max(Comparator.comparing(Endereco::getDataInicio))
                .orElse(null);

        return toResponseDTO(endereco);
    }

    public EnderecoResponseDTO toResponseDTO(Escola escola, LocalDate data) {
        if (escola == null) return null;

        Endereco endereco = escola.getEnderecos()
                .stream()
                .filter(e -> e.estaValidoEm(data))
                .max(Comparator.comparing(Endereco::getDataInicio))
                .orElse(null);

        return toResponseDTO(endereco);
    }

    public EnderecoResponseDTO toResponseDTO(Empresa empresa, LocalDate data) {
        if (empresa == null) return null;

        Endereco endereco = empresa.getEnderecos()
                .stream()
                .filter(e -> e.estaValidoEm(data))
                .max(Comparator.comparing(Endereco::getDataInicio))
                .orElse(null);

        return toResponseDTO(endereco);
    }

    public EnderecoResponseDTO toResponseDTO(Endereco endereco) {
        if (endereco == null) return null;

        return new EnderecoResponseDTO(
                endereco.getIdEndereco(),
                endereco.getCep(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getTerritorio().getResultado(),
                endereco.getDataInicio(),
                endereco.getDataFim()
        );
    }

    public EnderecoListarResponseDTO toListarResponseDTO(Endereco endereco) {
        if (endereco == null) return null;

        return new EnderecoListarResponseDTO(
                endereco.getIdEndereco(),
                endereco.getDataInicio(),
                endereco.getDataFim(),
                endereco.getTerritorio().getResultado()
        );
    }
}
