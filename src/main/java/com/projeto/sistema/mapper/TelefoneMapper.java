package com.projeto.sistema.mapper;

import com.projeto.sistema.dto.response.*;
import com.projeto.sistema.enums.TitularContato;
import com.projeto.sistema.model.Adolescente;
import com.projeto.sistema.model.Telefone;
import com.projeto.sistema.dto.response.TelefonesEmpresaResponseDTO;
import com.projeto.sistema.model.Empresa;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.Comparator;

@Component
public class TelefoneMapper {

    public TelefonesResponseDTO toResponseDTO(Adolescente adolescente, LocalDate data) {
        Telefone telAdolescente = adolescente.getTelefones()
                .stream()
                .filter(t -> t.getTitular() == TitularContato.ADOLESCENTE)
                .filter(t -> t.estaValidoEm(data))
                .max(Comparator.comparing(Telefone::getDataInicio))
                .orElse(null);

        Telefone telResponsavel = adolescente.getTelefones()
                .stream()
                .filter(t -> t.getTitular() == TitularContato.RESPONSAVEL)
                .filter(t -> t.estaValidoEm(data))
                .max(Comparator.comparing(Telefone::getDataInicio))
                .orElse(null);

        Telefone telExtra = adolescente.getTelefones()
                .stream()
                .filter(t -> t.getTitular() == TitularContato.EXTRA)
                .filter(t -> t.estaValidoEm(data))
                .max(Comparator.comparing(Telefone::getDataInicio))
                .orElse(null);


        return new TelefonesResponseDTO(
                telAdolescente != null ? toResponseDTO(telAdolescente) : null,
                telResponsavel != null ? toResponseDTO(telResponsavel) : null,
                telExtra != null ? toResponseDTO(telExtra) : null
        );
    }

    public TelefonesEmpresaResponseDTO toResponseDTO(Empresa empresa, LocalDate data) {
        Telefone telPrincipal = empresa.getTelefones()
                .stream()
                .filter(t -> t.getTitular() == TitularContato.EMPRESA)
                .filter(t -> t.estaValidoEm(data))
                .max(Comparator.comparing(Telefone::getDataInicio))
                .orElse(null);

        Telefone telExtra = empresa.getTelefones()
                .stream()
                .filter(t -> t.getTitular() == TitularContato.EXTRA)
                .filter(t -> t.estaValidoEm(data))
                .max(Comparator.comparing(Telefone::getDataInicio))
                .orElse(null);

        return new TelefonesEmpresaResponseDTO(
                telPrincipal != null ? toResponseDTO(telPrincipal) : null,
                telExtra != null ? toResponseDTO(telExtra) : null
        );
    }

    public TelefoneResponseDTO toResponseDTO(Telefone telefone) {
        return new TelefoneResponseDTO(
                telefone != null ? telefone.getIdTelefone() : null,
                telefone != null ? telefone.getNumero() : null,
                telefone != null ? telefone.getTitular() : null,
                telefone != null ? telefone.getDataInicio() : null,
                telefone != null ? telefone.getDataFim() : null
        );
    }

    public TelefoneListarResponseDTO toListarResponseDTO(Telefone telefone) {
        return new TelefoneListarResponseDTO(
                telefone != null ? telefone.getIdTelefone() : null,
                telefone != null ? telefone.getDataInicio() : null,
                telefone != null ? telefone.getDataFim() : null,
                telefone != null ? telefone.getNumero() : null
        );
    }
}
