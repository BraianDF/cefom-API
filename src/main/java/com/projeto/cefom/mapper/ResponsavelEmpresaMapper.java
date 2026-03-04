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
        if (empresa == null) return null;

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
                toResponseDTO(responsavelEmpresa),
                toResponseDTO(responsavelAprendizes),
                toResponseDTO(responsavelEntrevistas)
        );
    }

    public ResponsavelEmpresaResponseDTO toResponseDTO(ResponsavelEmpresa responsavel) {
        if (responsavel == null) return null;

        return new ResponsavelEmpresaResponseDTO(
                responsavel.getIdResponsavelEmpresa(),
                responsavel.getNome(),
                responsavel.getResponsabilidade(),
                responsavel.getDataInicio(),
                responsavel.getDataFim()
        );
    }

    public ResponsavelEmpresaListarResponseDTO toListarResponseDTO(ResponsavelEmpresa responsavel) {
        if (responsavel == null) return null;

        return new ResponsavelEmpresaListarResponseDTO(
                responsavel.getIdResponsavelEmpresa(),
                responsavel.getDataInicio(),
                responsavel.getDataFim(),
                responsavel.getNome(),

        );
    }

    public ResponsavelEmpresaListarResponseDTO toListarEntrevistaResponseDTO(Empresa empresa, LocalDate data) {
        if (empresa == null) return null;

        ResponsavelEmpresa responsavelEntrevistas = empresa.getResponsaveis()
                .stream()
                .filter(f -> f.getResponsabilidade() == TipoResponsabilidade.ENTREVISTAS)
                .filter(f -> f.estaValidoEm(data))
                .max(Comparator.comparing(ResponsavelEmpresa::getDataInicio))
                .orElse(null);

        return toListarResponseDTO(responsavelEntrevistas);
    }

    public ResponsavelEmpresaResponseDTO toResponseDTO (Entrevista entrevista) {
        if (entrevista == null) return null;

        ResponsavelEmpresa responsavelEntrevistas = entrevista.getEmpresa().getResponsaveis()
                .stream()
                .filter(f -> f.getResponsabilidade() == TipoResponsabilidade.ENTREVISTAS)
                .filter(f -> f.estaValidoEm(entrevista.getDataInicio()))
                .max(Comparator.comparing(ResponsavelEmpresa::getDataInicio))
                .orElse(null);

        return toResponseDTO(responsavelEntrevistas);
    }
}
