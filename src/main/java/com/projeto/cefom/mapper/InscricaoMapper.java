package com.projeto.cefom.mapper;

import com.projeto.cefom.dto.response.*;
import com.projeto.cefom.image.mapper.FotoAdolescenteMapper;
import com.projeto.cefom.model.*;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class InscricaoMapper {
    private final DocumentoMapper documentoMapper;
    private final EnderecoMapper enderecoMapper;
    private final EscolaridadeMapper escolaridadeMapper;
    private final TelefoneMapper telefoneMapper;
    private final FotoAdolescenteMapper fotoAdolescenteMapper;

    public InscricaoMapper(DocumentoMapper documentoMapper, EnderecoMapper enderecoMapper, EscolaridadeMapper escolaridadeMapper, TelefoneMapper telefoneMapper, FotoAdolescenteMapper fotoAdolescenteMapper) {
        this.documentoMapper = documentoMapper;
        this.enderecoMapper = enderecoMapper;
        this.escolaridadeMapper = escolaridadeMapper;
        this.telefoneMapper = telefoneMapper;
        this.fotoAdolescenteMapper = fotoAdolescenteMapper;
    }

    public InscricaoCriarResponseDTO toResponseDTO(Adolescente adolescente, LocalDate data) {
        return new InscricaoCriarResponseDTO(
                // Inscricao
                toResponseDTO(null),

                // Documento
                documentoMapper.toInscricaoResponseDTO(adolescente),

                // Adolescente
                toAdolescenteResponseDTO(adolescente, data),

                // Endereço
                enderecoMapper.toInscricaoResponseDTO(adolescente, data),

                // Escolaridade
                escolaridadeMapper.toResponseDTO(adolescente, data),

                // Telefones
                telefoneMapper.toResponseDTO(adolescente, data)
        );
    }

    public InscricaoCriarResponseDTO toResponseDTO(Inscricao inscricao, LocalDate data) {
        Adolescente adolescente = inscricao.getAdolescente();

        return new InscricaoCriarResponseDTO(
                // Inscricao
                toResponseDTO(inscricao),

                // Documento
                documentoMapper.toInscricaoResponseDTO(adolescente),

                // Adolescente
                toAdolescenteResponseDTO(adolescente, data),

                // Endereço
                enderecoMapper.toInscricaoResponseDTO(adolescente, data),

                // Escolaridade
                escolaridadeMapper.toResponseDTO(adolescente, data),

                // Telefones
                telefoneMapper.toResponseDTO(adolescente, data)
        );
    }

    public InscricaoResponseDTO toResponseDTO(Inscricao inscricao) {
        return new InscricaoResponseDTO(
                inscricao != null ? inscricao.getIdInscricao() : null,
                inscricao != null ? inscricao.getNumInscricao() : null,
                inscricao != null ? inscricao.getObservacao() : null,
                inscricao != null ? inscricao.getDataInicio() : null,
                inscricao != null ? inscricao.getDataFim() : null,
                inscricao != null ? inscricao.getFinalizacao() : null,
                inscricao != null ? fotoAdolescenteMapper.toResponseDTO(inscricao.getFoto()) : null
        );
    }

    public InscricaoListarResponseDTO toListarResponseDTO(Inscricao inscricao) {
        return new InscricaoListarResponseDTO(
                inscricao != null ? inscricao.getIdInscricao() : null,
                inscricao != null ? inscricao.getNumInscricao() : null,
                inscricao != null ? inscricao.getDataInicio() : null,
                inscricao != null ? inscricao.getDataFim() : null
        );
    }

    public AdolescenteInscricaoResponseDTO toAdolescenteResponseDTO(Adolescente adolescente, LocalDate data) {
        if (adolescente == null) return null;
        return new AdolescenteInscricaoResponseDTO(
                adolescente.getIdAdolescente(),
                adolescente.getNome(),
                adolescente.getGenero(),
                adolescente.getDataNascimento(),
                adolescente.getIdadeEm(data),
                adolescente.getSituacaoEm(data)
        );
    }
}
