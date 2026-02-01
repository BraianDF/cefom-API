package com.projeto.sistema.mapper;

import com.projeto.sistema.dto.response.*;
import com.projeto.sistema.image.FotoAdolescenteMapper;
import com.projeto.sistema.model.*;
import com.projeto.sistema.dto.response.AdolescenteEntrevistaResponseDTO;
import com.projeto.sistema.dto.response.AdolescenteMatriculaEntrevistaResponseDTO;
import com.projeto.sistema.model.Entrevista;
import com.projeto.sistema.model.VinculoEntrevistaMatricula;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class MatriculaMapper {

    private final DocumentoMapper documentoMapper;
    private final EnderecoMapper enderecoMapper;
    private final EscolaridadeMapper escolaridadeMapper;
    private final TelefoneMapper telefoneMapper;
    private final EmailMapper emailMapper;
    private final CaracteristicaMapper caracteristicaMapper;
    private final DadosSocialMapper dadosSocialMapper;
    private final FamiliarMapper familiarMapper;
    private final VinculoEntrevistaMatriculaMapper vinculoEntrevistaMatriculaMapper;
    private final FotoAdolescenteMapper fotoAdolescenteMapper;

    public MatriculaMapper(DocumentoMapper documentoMapper, EnderecoMapper enderecoMapper, EscolaridadeMapper escolaridadeMapper, TelefoneMapper telefoneMapper, EmailMapper emailMapper, CaracteristicaMapper caracteristicaMapper, DadosSocialMapper dadosSocialMapper, FamiliarMapper familiarMapper, VinculoEntrevistaMatriculaMapper vinculoEntrevistaMatriculaMapper, FotoAdolescenteMapper fotoAdolescenteMapper) {
        this.documentoMapper = documentoMapper;
        this.enderecoMapper = enderecoMapper;
        this.escolaridadeMapper = escolaridadeMapper;
        this.telefoneMapper = telefoneMapper;
        this.emailMapper = emailMapper;
        this.caracteristicaMapper = caracteristicaMapper;
        this.dadosSocialMapper = dadosSocialMapper;
        this.familiarMapper = familiarMapper;
        this.vinculoEntrevistaMatriculaMapper = vinculoEntrevistaMatriculaMapper;
        this.fotoAdolescenteMapper = fotoAdolescenteMapper;
    }

    public MatriculaCriarResponseDTO toResponseDTO(Adolescente adolescente, LocalDate data) {
        return new MatriculaCriarResponseDTO(
                toResponseDTO(null),

                toAdolescenteResponseDTO(adolescente, data),

                enderecoMapper.toResponseDTO(adolescente, data),

                telefoneMapper.toResponseDTO(adolescente, data),

                emailMapper.toResponseDTO(adolescente, data),

                documentoMapper.toResponseDTO(adolescente),

                caracteristicaMapper.toResponseDTO(adolescente, data),

                escolaridadeMapper.toResponseDTO(adolescente, data),

                dadosSocialMapper.toResponseDTO(adolescente, data),

                familiarMapper.toResponseDTO(adolescente, data)
        );
    }

    public MatriculaCriarResponseDTO toResponseDTO(Matricula matricula, LocalDate data) {
        Adolescente adolescente = matricula.getAdolescente();

        return new MatriculaCriarResponseDTO(

                toResponseDTO(matricula),

                toAdolescenteResponseDTO(adolescente, data),

                enderecoMapper.toResponseDTO(adolescente, data),

                telefoneMapper.toResponseDTO(adolescente, data),

                emailMapper.toResponseDTO(adolescente, data),

                documentoMapper.toResponseDTO(adolescente),

                caracteristicaMapper.toResponseDTO(adolescente, data),

                escolaridadeMapper.toResponseDTO(adolescente, data),

                dadosSocialMapper.toResponseDTO(adolescente, data),

                familiarMapper.toResponseDTO(adolescente, data)
        );
    }

    public MatriculaResponseDTO toResponseDTO(Matricula matricula) {
        return new MatriculaResponseDTO(
                matricula != null ? matricula.getIdMatricula() : null,
                matricula != null ? matricula.getNumMatricula() : null,
                matricula != null ? matricula.getSituacaoMatricula() : null,
                matricula != null ? matricula.getDataInicio() : null,
                matricula != null ? matricula.getDataFim() : null,
                matricula != null ? matricula.getDesligamento() : null,
                matricula != null ? fotoAdolescenteMapper.toResponseDTO(matricula.getFoto()) : null
        );
    }

    public MatriculaListarResponseDTO toListarResponseDTO(Matricula matricula) {
        return new MatriculaListarResponseDTO(
                matricula != null ? matricula.getIdMatricula() : null,
                matricula != null ? matricula.getNumMatricula() : null,
                matricula != null ? matricula.getDataInicio() : null,
                matricula != null ? matricula.getDataFim() : null
        );
    }

    public AdolescenteEntrevistaResponseDTO toEntrevistaResponseDTO(Matricula matricula, Entrevista entrevista) {
        if (matricula == null) return null;

        VinculoEntrevistaMatricula vinculo = matricula.getEntrevistas().stream()
                .filter(v -> v.getEntrevista().getIdEntrevista().equals(entrevista.getIdEntrevista()))
                .findFirst()
                .orElse(null);

        return new AdolescenteEntrevistaResponseDTO(
                vinculoEntrevistaMatriculaMapper.toResponseDTO(vinculo),
                new AdolescenteMatriculaEntrevistaResponseDTO(
                        matricula.getAdolescente().getIdAdolescente(),
                        matricula.getIdMatricula(),
                        matricula.getNumMatricula(),
                        matricula.getAdolescente().getNome(),
                        matricula.getAdolescente().getDataNascimento(),
                        matricula.getAdolescente().getIdadeEm(entrevista.getDataInicio()),
                        matricula.getAdolescente().getSituacaoEm(entrevista.getDataInicio())
                        ),
                documentoMapper.toEntrevistaResponseDTO(matricula.getAdolescente()),
                enderecoMapper.toInscricaoResponseDTO(matricula.getAdolescente(), entrevista.getDataInicio()),
                escolaridadeMapper.toResponseDTO(matricula.getAdolescente(), entrevista.getDataInicio())
        );
    }

    public AdolescenteMatriculaResponseDTO toAdolescenteResponseDTO(Adolescente adolescente, LocalDate data) {
        if (adolescente == null) return null;
        return new AdolescenteMatriculaResponseDTO(
                adolescente.getIdAdolescente(),
                adolescente.getNome(),
                adolescente.getGenero(),
                adolescente.getDataNascimento(),
                adolescente.getCidadeNascimento(),
                adolescente.getEstadoNascimento(),
                adolescente.getPaisNascimento(),
                adolescente.getNaturalidade(),
                adolescente.getEstadoCivil(),
                adolescente.getMae(),
                adolescente.getPai(),
                adolescente.getPai(),
                adolescente.getIdadeEm(data),
                adolescente.getSituacaoEm(data)
        );
    }


}
