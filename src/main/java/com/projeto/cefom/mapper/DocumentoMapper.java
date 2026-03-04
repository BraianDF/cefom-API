package com.projeto.cefom.mapper;

import com.projeto.cefom.dto.response.DocumentoAdolescenteResponseDTO;
import com.projeto.cefom.dto.response.DocumentoInscricaoResponseDTO;
import com.projeto.cefom.dto.response.DocumentoResponsavelResponseDTO;
import com.projeto.cefom.model.Adolescente;
import com.projeto.cefom.model.Documento;
import com.projeto.cefom.dto.response.DocumentoAdolescenteEntrevistaResponseDTO;
import com.projeto.cefom.dto.response.DocumentoEmpresaResponseDTO;
import com.projeto.cefom.model.Empresa;
import org.springframework.stereotype.Component;

@Component
public class DocumentoMapper {

    public DocumentoAdolescenteResponseDTO toResponseDTO(Adolescente adolescente) {
        if (adolescente == null) return null;

        Documento documento = adolescente.getDocumento();

        return toResponseDTO(documento);
    }

    public DocumentoAdolescenteResponseDTO toResponseDTO(Documento documento) {
        if (documento == null) return null;

        return new DocumentoAdolescenteResponseDTO(
                documento.getIdDocumento(),
                documento.getCpf(),
                documento.getCtps(),
                documento.getRg(),
                documento.getDataEmissaoRG(),
                documento.getNis(),
                documento.getSus(),
                documento.getTituloEleitor(),
                documento.getZonaTE(),
                documento.getSecaoTE(),
                documento.getCnh(),
                documento.getCategoriaCNH(),
                documento.getReservista()
        );
    }

    public DocumentoInscricaoResponseDTO toInscricaoResponseDTO(Adolescente adolescente) {
        if (adolescente == null) return null;

        Documento documento = adolescente.getDocumento();

        return toInscricaoResponseDTO(documento);
    }

    public DocumentoEmpresaResponseDTO toResponseDTO(Empresa empresa) {
        if (empresa == null) return null;

        Documento documento = empresa.getDocumento();

        return toEmpresaResponseDTO(documento);
    }

    public DocumentoInscricaoResponseDTO toInscricaoResponseDTO(Documento documento) {
        if (documento == null) return null;

        return new DocumentoInscricaoResponseDTO(
                documento.getIdDocumento(),
                documento.getCpf()
        );
    }

    public DocumentoEmpresaResponseDTO toEmpresaResponseDTO(Documento documento) {
        if (documento == null) return null;

        return new DocumentoEmpresaResponseDTO(
                documento != null ? documento.getIdDocumento() : null,
                documento != null ? documento.getCpf() != null ? documento.getCpf() : documento.getCnpj() : null
        );
    }

    public DocumentoResponsavelResponseDTO toResponsavelResponseDTO(Documento documento) {
        if (documento == null) return null;

        return new DocumentoResponsavelResponseDTO(
                documento.getIdDocumento(),
                documento.getCpf(),
                documento.getRg(),
                documento.getNis()
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
