package com.projeto.cefom.mapper;

import com.projeto.cefom.dto.response.*;
import com.projeto.cefom.enums.TitularContato;
import com.projeto.cefom.model.Adolescente;
import com.projeto.cefom.model.Telefone;
import com.projeto.cefom.dto.response.TelefonesEmpresaResponseDTO;
import com.projeto.cefom.model.Empresa;
import com.projeto.cefom.utils.TextoUtils;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.Comparator;

@Component
public class TelefoneMapper {

    public TelefonesResponseDTO toResponseDTO(Adolescente adolescente, LocalDate data) {
        if (adolescente == null) return null;

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
                toResponseDTO(telAdolescente),
                toResponseDTO(telResponsavel),
                toResponseDTO(telExtra)
        );
    }

    public TelefonesEmpresaResponseDTO toResponseDTO(Empresa empresa, LocalDate data) {
        if (empresa == null) return null;

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
                toResponseDTO(telPrincipal),
                toResponseDTO(telExtra)
        );
    }

    public TelefoneResponseDTO toResponseDTO(Telefone telefone) {
        if (telefone == null) return null;

        return new TelefoneResponseDTO(
                telefone.getIdTelefone(),
                TextoUtils.formatarTelefone(telefone.getNumero()),
                telefone.getTitular(),
                telefone.getDataInicio(),
                telefone.getDataFim()
        );
    }

    public TelefoneListarResponseDTO toListarResponseDTO(Telefone telefone) {
        if (telefone == null) return null;

        return new TelefoneListarResponseDTO(
                telefone.getIdTelefone(),
                telefone.getDataInicio(),
                telefone.getDataFim(),
                TextoUtils.formatarTelefone(telefone.getNumero())
        );
    }
}
