package com.projeto.cefom.mapper;

import com.projeto.cefom.dto.response.*;
import com.projeto.cefom.model.Adolescente;
import com.projeto.cefom.model.Familiar;
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
        if (adolescente == null) return null;

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
        if (familiar == null) return null;

        return new ResponsavelResponseDTO(
                familiar.getIdFamiliar(),
                familiar.getNome(),
                familiar.getParentesco(),
                familiar.getIdade(),
                familiar.getEscolaridade(),
                familiar.getProfissao(),
                familiar.getLocalTrabalho(),
                familiar.getRenda(),
                familiar.getReside(),
                documentoMapper.toResponsavelResponseDTO(familiar.getDocumento()),
                familiar.getNaturalidade(),
                familiar.getEstadoCivil(),
                familiar.getDataInicio(),
                familiar.getDataFim()
        );
    }

    public FamiliarListarResponseDTO toListarResponseDTO(Familiar familiar) {
        if (familiar == null) return null;

        return new FamiliarListarResponseDTO(
                familiar.getIdFamiliar(),
                familiar.getDataInicio(),
                familiar.getDataFim(),
                familiar.getNome(),
                familiar.isResponsavel()
        );
    }

    public FamiliarResponseDTO toFamiliarResponseDTO(Familiar familiar) {
        if (familiar == null) return null;

        return new FamiliarResponseDTO(
                familiar.getIdFamiliar(),
                familiar.getNome(),
                familiar.getParentesco(),
                familiar.getIdade(),
                familiar.getEscolaridade(),
                familiar.getProfissao(),
                familiar.getLocalTrabalho(),
                familiar.getRenda(),
                familiar.getDataInicio(),
                familiar.getDataFim()
        );
    }
}
