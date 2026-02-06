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
        TaxaAdministrativa taxa = empresa.getTaxasAdministrativas()
                .stream()
                .filter(f -> f.estaValidoEm(data))
                .max(Comparator.comparing(TaxaAdministrativa::getDataInicio))
                .orElse(null);

        return taxa != null ? toResponseDTO(taxa) : null;
    }

    public TaxaAdministrativaResponseDTO toResponseDTO(TaxaAdministrativa taxa) {
        return new TaxaAdministrativaResponseDTO(
                taxa != null ? taxa.getIdTaxaAdministrativa() : null,
                taxa != null ? taxa.getValorTaxa() : null,
                taxa != null ? taxa.getDataInicio() : null,
                taxa != null ? taxa.getDataFim() : null
        );
    }

    public TaxaAdministrativaListarResponseDTO toListarResponseDTO(TaxaAdministrativa taxa) {
        return new TaxaAdministrativaListarResponseDTO(
                taxa != null ? taxa.getIdTaxaAdministrativa() : null,
                taxa != null ? taxa.getDataInicio() : null,
                taxa != null ? taxa.getDataFim() : null,
                taxa != null ? taxa.getValorTaxa() : null
        );
    }
}
