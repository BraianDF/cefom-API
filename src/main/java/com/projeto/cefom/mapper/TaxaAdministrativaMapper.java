package com.projeto.cefom.mapper;

import com.projeto.cefom.dto.response.TaxaAdministrativaListarResponseDTO;
import com.projeto.cefom.dto.response.TaxaAdministrativaResponseDTO;
import com.projeto.cefom.model.Empresa;
import com.projeto.cefom.model.TaxaAdministrativa;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Comparator;

@Component
public class TaxaAdministrativaMapper {

    public TaxaAdministrativaResponseDTO toResponseDTO(Empresa empresa, LocalDate data) {
        if (empresa == null) return null;

        TaxaAdministrativa taxa = empresa.getTaxasAdministrativas()
                .stream()
                .filter(f -> f.estaValidoEm(data))
                .max(Comparator.comparing(TaxaAdministrativa::getDataInicio))
                .orElse(null);

        return taxa != null ? toResponseDTO(taxa) : null;
    }

    public TaxaAdministrativaResponseDTO toResponseDTO(TaxaAdministrativa taxa) {
        if (taxa == null) return null;

        return new TaxaAdministrativaResponseDTO(
                taxa.getIdTaxaAdministrativa(),
                taxa.getValorTaxa(),
                taxa.getDataInicio(),
                taxa.getDataFim()
        );
    }

    public TaxaAdministrativaListarResponseDTO toListarResponseDTO(TaxaAdministrativa taxa) {
        if (taxa == null) return null;

        return new TaxaAdministrativaListarResponseDTO(
                taxa.getIdTaxaAdministrativa(),
                taxa.getDataInicio(),
                taxa.getDataFim(),
                taxa.getValorTaxa()
        );
    }
}
