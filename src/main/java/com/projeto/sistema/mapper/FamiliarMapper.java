package com.projeto.sistema.mapper;

import com.projeto.sistema.dto.response.*;
import com.projeto.sistema.model.Adolescente;
import com.projeto.sistema.model.Familiar;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Component
public class FamiliarMapper {

    private final DocumentoMapper documentoMapper;

    public FamiliarMapper(DocumentoMapper documentoMapper) {
        this.documentoMapper = documentoMapper;
    }

    public FamiliaresResponseDTO toResponseDTO(Adolescente adolescente, LocalDate data) {
        List<Familiar> familiares = adolescente.getFamiliares()
                .stream()
                .filter(f -> f.estaValidoEm(data))
                .filter(f -> !f.isResponsavel())
                .toList();

        Familiar responsavel = adolescente.getFamiliares()
                .stream()
                .filter(f -> f.estaValidoEm(data))
                .filter(Familiar::isResponsavel)
                .max(Comparator.comparing(Familiar::getDataInicio))
                .orElse(null);

        List<FamiliarResponseDTO> familiaresDTO = familiares
                .stream()
                .map(this::toFamiliarResponseDTO)
                .toList();

        return new FamiliaresResponseDTO(
                toResponseDTO(responsavel),
                familiaresDTO
        );
    }

    public ResponsavelResponseDTO toResponseDTO(Familiar familiar) {
        return new ResponsavelResponseDTO(
                familiar != null ? familiar.getIdFamiliar() : null,
                familiar != null ? familiar.getNome() : null,
                familiar != null ? familiar.getParentesco() : null,
                familiar != null ? familiar.getIdade() : null,
                familiar != null ? familiar.getEscolaridade() : null,
                familiar != null ? familiar.getProfissao() : null,
                familiar != null ? familiar.getLocalTrabalho() : null,
                familiar != null ? familiar.getRenda() : null,
                familiar != null ? familiar.getReside() : null,
                familiar != null && familiar.isResponsavel() ? documentoMapper.toResponsavelResponseDTO(familiar.getDocumento()) : null,
                familiar != null && familiar.isResponsavel() ? familiar.getNaturalidade() : null,
                familiar != null && familiar.isResponsavel() ? familiar.getEstadoCivil() : null,
                familiar != null ? familiar.getDataInicio() : null,
                familiar != null ? familiar.getDataFim() : null
        );
    }

    public FamiliarListarResponseDTO toListarResponseDTO(Familiar familiar) {
        return new FamiliarListarResponseDTO(
                familiar != null ? familiar.getIdFamiliar() : null,
                familiar != null ? familiar.getDataInicio() : null,
                familiar != null ? familiar.getDataFim() : null,
                familiar != null ? familiar.getNome() : null,
                familiar != null ? familiar.getDocumento() != null : null
        );
    }

    public FamiliarResponseDTO toFamiliarResponseDTO(Familiar familiar) {
        return new FamiliarResponseDTO(
                familiar != null ? familiar.getIdFamiliar() : null,
                familiar != null ? familiar.getNome() : null,
                familiar != null ? familiar.getParentesco() : null,
                familiar != null ? familiar.getIdade() : null,
                familiar != null ? familiar.getEscolaridade() : null,
                familiar != null ? familiar.getProfissao() : null,
                familiar != null ? familiar.getLocalTrabalho() : null,
                familiar != null ? familiar.getRenda() : null,
                familiar != null ? familiar.getDataInicio() : null,
                familiar != null ? familiar.getDataFim() : null
        );
    }
}
