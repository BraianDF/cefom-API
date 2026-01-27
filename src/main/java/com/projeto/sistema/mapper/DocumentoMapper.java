package com.projeto.sistema.mapper;

import com.projeto.sistema.dto.response.DocumentoAdolescenteResponseDTO;
import com.projeto.sistema.dto.response.DocumentoInscricaoResponseDTO;
import com.projeto.sistema.dto.response.DocumentoResponsavelResponseDTO;
import com.projeto.sistema.model.Adolescente;
import com.projeto.sistema.model.Documento;
import com.projeto.sistema.dto.response.DocumentoAdolescenteEntrevistaResponseDTO;
import com.projeto.sistema.dto.response.DocumentoEmpresaResponseDTO;
import com.projeto.sistema.model.Empresa;
import org.springframework.stereotype.Component;

@Component
public class DocumentoMapper {

    public DocumentoAdolescenteResponseDTO toResponseDTO(Adolescente adolescente) {
        Documento documento = adolescente.getDocumento();

        return toResponseDTO(documento);
    }

    public DocumentoAdolescenteResponseDTO toResponseDTO(Documento documento) {
        return new DocumentoAdolescenteResponseDTO(
                documento != null ? documento.getIdDocumento() : null,
                documento != null ? documento.getCpf() : null,
                documento != null ? documento.getCtps() : null,
                documento != null ? documento.getRg() : null,
                documento != null ? documento.getDataEmissaoRG() : null,
                documento != null ? documento.getNis() : null,
                documento != null ? documento.getSus() : null,
                documento != null ? documento.getTituloEleitor() : null,
                documento != null ? documento.getZonaTE() : null,
                documento != null ? documento.getSecaoTE() : null,
                documento != null ? documento.getCnh() : null,
                documento != null ? documento.getCategoriaCNH() : null,
                documento != null ? documento.getReservista() : null
        );
    }

    public DocumentoInscricaoResponseDTO toInscricaoResponseDTO(Adolescente adolescente) {
        Documento documento = adolescente.getDocumento();

        return toInscricaoResponseDTO(documento);
    }

    public DocumentoEmpresaResponseDTO toResponseDTO(Empresa empresa) {
        Documento documento = empresa.getDocumento();

        return toEmpresaResponseDTO(documento);
    }

    public DocumentoInscricaoResponseDTO toInscricaoResponseDTO(Documento documento) {
        return new DocumentoInscricaoResponseDTO(
                documento != null ? documento.getIdDocumento() : null,
                documento != null ? documento.getCpf() : null
        );
    }

    public DocumentoEmpresaResponseDTO toEmpresaResponseDTO(Documento documento) {
        return new DocumentoEmpresaResponseDTO(
                documento != null ? documento.getIdDocumento() : null,
                documento != null ? documento.getCpf() != null ? documento.getCpf() : documento.getCnpj() : null
        );
    }

    public DocumentoResponsavelResponseDTO toResponsavelResponseDTO(Documento documento) {
        return new DocumentoResponsavelResponseDTO(
                documento != null ? documento.getIdDocumento() : null,
                documento != null ? documento.getCpf() : null,
                documento != null ? documento.getRg() : null,
                documento != null ? documento.getNis() : null
        );
    }

    public DocumentoAdolescenteEntrevistaResponseDTO toEntrevistaResponseDTO(Adolescente adolescente) {
        if (adolescente == null) return null;
        return new DocumentoAdolescenteEntrevistaResponseDTO(
                adolescente.getDocumento().getIdDocumento(),
                adolescente.getDocumento().getCpf(),
                adolescente.getDocumento().getRg()
        );
    }
}
