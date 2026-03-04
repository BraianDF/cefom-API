package com.projeto.cefom.mapper;

import com.projeto.cefom.dto.response.*;
import com.projeto.cefom.enums.TipoResponsabilidade;
import com.projeto.cefom.model.Empresa;
import com.projeto.cefom.model.Entrevista;
import com.projeto.cefom.model.ResponsavelEmpresa;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Comparator;

@Component
public class EmpresaMapper {

    private final DocumentoMapper documentoMapper;
    private final ResponsavelEmpresaMapper responsavelEmpresaMapper;
    private final TelefoneMapper telefoneMapper;
    private final EmailMapper emailMapper;
    private final EnderecoMapper enderecoMapper;
    private final TaxaAdministrativaMapper taxaAdministrativaMapper;

    public EmpresaMapper(DocumentoMapper documentoMapper, ResponsavelEmpresaMapper responsavelEmpresaMapper, TelefoneMapper telefoneMapper, EmailMapper emailMapper, EnderecoMapper enderecoMapper, TaxaAdministrativaMapper taxaAdministrativaMapper) {
        this.documentoMapper = documentoMapper;
        this.responsavelEmpresaMapper = responsavelEmpresaMapper;
        this.telefoneMapper = telefoneMapper;
        this.emailMapper = emailMapper;
        this.enderecoMapper = enderecoMapper;
        this.taxaAdministrativaMapper = taxaAdministrativaMapper;
    }

    public EmpresaCriarResponseDTO toResponseDTO(Empresa empresa, LocalDate data) {
        if (empresa == null) return null;

        return new EmpresaCriarResponseDTO(
                toResponseDTO(empresa),

                documentoMapper.toResponseDTO(empresa),

                taxaAdministrativaMapper.toResponseDTO(empresa, data),

                emailMapper.toResponseDTO(empresa, data),

                telefoneMapper.toResponseDTO(empresa, data),

                enderecoMapper.toResponseDTO(empresa, data),

                responsavelEmpresaMapper.toResponseDTO(empresa, data)
        );
    }

    public EmpresaResponseDTO toResponseDTO(Empresa empresa) {
        if (empresa == null) return null;
        return new EmpresaResponseDTO(
                empresa.getIdEmpresa(),
                empresa.getNumConvenio(),
                empresa.getRazaoSocial(),
                empresa.getNomeFantasia(),
                empresa.getApelido(),
                empresa.getTipoEmpresa(),
                empresa.getContratacaoPadrao()
        );
    }

    public EmpresaListarResponseDTO toListarResponseDTO(Empresa empresa) {
        if (empresa == null) return null;
        return new EmpresaListarResponseDTO(
                empresa.getIdEmpresa(),
                empresa.getApelido(),
                empresa.getTipoEmpresa()
        );
    }

    public EmpresaSelectResponseDTO toSelectResponseDTO(Empresa empresa) {
        if (empresa == null) return null;

        ResponsavelEmpresa responsavelEntrevistas = empresa.getResponsaveis()
                .stream()
                .filter(f -> f.getResponsabilidade() == TipoResponsabilidade.ENTREVISTAS)
                .filter(f -> f.estaValidoEm(LocalDate.now()))
                .max(Comparator.comparing(ResponsavelEmpresa::getDataInicio))
                .orElse(null);

        return new EmpresaSelectResponseDTO(
                empresa.getIdEmpresa(),
                empresa.getApelido(),
                responsavelEntrevistas.getNome(),
                empresa.getContratacaoPadrao()
        );
    }

    public EmpresaListarEntrevistaResponseDTO toListarEntrevistaResponseDTO(Empresa empresa) {
        if (empresa == null) return null;
        return new EmpresaListarEntrevistaResponseDTO(
                empresa.getIdEmpresa(),
                empresa.getApelido(),
                responsavelEmpresaMapper.toListarEntrevistaResponseDTO(empresa, LocalDate.now())
        );
    }

    public EmpresaEntrevistaResponseDTO toResponseDTO(Entrevista entrevista) {
        if (entrevista == null) return null;
        return new EmpresaEntrevistaResponseDTO(
                toResponseDTO(entrevista.getEmpresa()),
                responsavelEmpresaMapper.toResponseDTO(entrevista)
        );
    }
}
