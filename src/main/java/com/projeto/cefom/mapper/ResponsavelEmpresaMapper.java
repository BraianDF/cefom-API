package com.projeto.cefom.mapper;

import com.projeto.cefom.dto.response.ResponsaveisEmpresaResponseDTO;
import com.projeto.cefom.dto.response.ResponsavelEmpresaListarResponseDTO;
import com.projeto.cefom.dto.response.ResponsavelEmpresaResponseDTO;
import com.projeto.cefom.enums.TipoResponsabilidade;
import com.projeto.cefom.model.Empresa;
import com.projeto.cefom.model.Entrevista;
import com.projeto.cefom.model.ResponsavelEmpresa;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Comparator;

@Component
public class ResponsavelEmpresaMapper {

    public ResponsaveisEmpresaResponseDTO toResponseDTO(Empresa empresa, LocalDate data) {
        ResponsavelEmpresa responsavelEmpresa = empresa.getResponsaveis()
                .stream()
                .filter(f -> f.getResponsabilidade() == TipoResponsabilidade.EMPRESA)
                .filter(f -> f.estaValidoEm(data))
                .max(Comparator.comparing(ResponsavelEmpresa::getDataInicio))
                .orElse(null);

        ResponsavelEmpresa responsavelAprendizes = empresa.getResponsaveis()
                .stream()
                .filter(f -> f.getResponsabilidade() == TipoResponsabilidade.APRENDIZES)
                .filter(f -> f.estaValidoEm(data))
                .max(Comparator.comparing(ResponsavelEmpresa::getDataInicio))
                .orElse(null);

        ResponsavelEmpresa responsavelEntrevistas = empresa.getResponsaveis()
                .stream()
                .filter(f -> f.getResponsabilidade() == TipoResponsabilidade.ENTREVISTAS)
                .filter(f -> f.estaValidoEm(data))
                .max(Comparator.comparing(ResponsavelEmpresa::getDataInicio))
                .orElse(null);


        return new ResponsaveisEmpresaResponseDTO(
                responsavelEmpresa != null ? toResponseDTO(responsavelEmpresa) : null,
                responsavelAprendizes != null ? toResponseDTO(responsavelAprendizes) : null,
                responsavelEntrevistas != null ? toResponseDTO(responsavelEntrevistas) : null
        );
    }

    public ResponsavelEmpresaResponseDTO toResponseDTO(ResponsavelEmpresa responsavel) {
        return new ResponsavelEmpresaResponseDTO(
                responsavel != null ? responsavel.getIdResponsavelEmpresa() : null,
                responsavel != null ? responsavel.getNome() : null,
                responsavel != null ? responsavel.getResponsabilidade() : null,
                responsavel != null ? responsavel.getDataInicio() : null,
                responsavel != null ? responsavel.getDataFim() : null
        );
    }

    public ResponsavelEmpresaListarResponseDTO toListarResponseDTO(ResponsavelEmpresa responsavel) {
        return new ResponsavelEmpresaListarResponseDTO(
                responsavel != null ? responsavel.getIdResponsavelEmpresa() : null,
                responsavel != null ? responsavel.getDataInicio() : null,
                responsavel != null ? responsavel.getDataFim() : null,
                responsavel != null ? responsavel.getNome() : null

        );
    }

    public ResponsavelEmpresaListarResponseDTO toListarEntrevistaResponseDTO(Empresa empresa, LocalDate data) {
        ResponsavelEmpresa responsavelEntrevistas = empresa.getResponsaveis()
                .stream()
                .filter(f -> f.getResponsabilidade() == TipoResponsabilidade.ENTREVISTAS)
                .filter(f -> f.estaValidoEm(data))
                .max(Comparator.comparing(ResponsavelEmpresa::getDataInicio))
                .orElse(null);

        return toListarResponseDTO(responsavelEntrevistas);
    }

    public ResponsavelEmpresaResponseDTO toResponseDTO (Entrevista entrevista) {
        ResponsavelEmpresa responsavelEntrevistas = entrevista.getEmpresa().getResponsaveis()
                .stream()
                .filter(f -> f.getResponsabilidade() == TipoResponsabilidade.ENTREVISTAS)
                .filter(f -> f.estaValidoEm(entrevista.getDataInicio()))
                .max(Comparator.comparing(ResponsavelEmpresa::getDataInicio))
                .orElse(null);

        return toResponseDTO(responsavelEntrevistas);
    }
}
