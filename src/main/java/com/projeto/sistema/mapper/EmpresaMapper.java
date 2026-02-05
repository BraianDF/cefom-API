package com.projeto.sistema.mapper;

import com.projeto.sistema.dto.response.*;
import com.projeto.sistema.enums.TipoResponsabilidade;
import com.projeto.sistema.model.Empresa;
import com.projeto.sistema.model.Entrevista;
import com.projeto.sistema.model.ResponsavelEmpresa;
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
        return new EmpresaCriarResponseDTO(
                empresa != null ? toResponseDTO(empresa) : null,

                empresa != null ? documentoMapper.toResponseDTO(empresa) : null,

                empresa != null ? taxaAdministrativaMapper.toResponseDTO(empresa, data) : null,

                empresa != null ? emailMapper.toResponseDTO(empresa, data) : null,

                empresa != null ? telefoneMapper.toResponseDTO(empresa, data) : null,

                empresa != null ? enderecoMapper.toResponseDTO(empresa, data) : null,

                empresa != null ? responsavelEmpresaMapper.toResponseDTO(empresa, data) : null
        );
    }

    public EmpresaResponseDTO toResponseDTO(Empresa empresa) {
        return new EmpresaResponseDTO(
                empresa != null ? empresa.getIdEmpresa() : null,
                empresa != null ? empresa.getNumConvenio() : null,
                empresa != null ? empresa.getRazaoSocial() : null,
                empresa != null ? empresa.getNomeFantasia() : null,
                empresa != null ? empresa.getApelido() : null,
                empresa != null ? empresa.getTipoEmpresa() : null,
                empresa != null ? empresa.getContratacaoPadrao() : null
        );
    }

    public EmpresaListarResponseDTO toListarResponseDTO(Empresa empresa) {
        return new EmpresaListarResponseDTO(
                empresa != null ? empresa.getIdEmpresa() : null,
                empresa != null ? empresa.getApelido() : null,
                empresa != null ? empresa.getTipoEmpresa() : null
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
        return new EmpresaListarEntrevistaResponseDTO(
                empresa != null ? empresa.getIdEmpresa() : null,
                empresa != null ? empresa.getApelido() : null,
                responsavelEmpresaMapper.toListarEntrevistaResponseDTO(empresa, LocalDate.now())
        );
    }

    public EmpresaEntrevistaResponseDTO toResponseDTO(Entrevista entrevista) {
        return new EmpresaEntrevistaResponseDTO(
                toResponseDTO(entrevista.getEmpresa()),
                responsavelEmpresaMapper.toResponseDTO(entrevista)
        );
    }
}
