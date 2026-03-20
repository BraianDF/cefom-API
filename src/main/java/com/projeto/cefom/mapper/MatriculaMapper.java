package com.projeto.cefom.mapper;

import com.projeto.cefom.dto.response.*;
import com.projeto.cefom.image.mapper.FotoAdolescenteMapper;
import com.projeto.cefom.model.*;
import com.projeto.cefom.dto.response.AdolescenteEntrevistaResponseDTO;
import com.projeto.cefom.dto.response.AdolescenteMatriculaEntrevistaResponseDTO;
import com.projeto.cefom.model.Entrevista;
import com.projeto.cefom.model.VinculoEntrevistaMatricula;
import com.projeto.cefom.dto.response.AlunoMatriculaResponseDTO;
import com.projeto.cefom.utils.TextoUtils;
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
        if (adolescente == null) return null;

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
        if (matricula == null) return null;

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
        if (matricula == null) return null;

        return new MatriculaResponseDTO(
                matricula.getIdMatricula(),
                matricula.getNumMatricula(),
                matricula.getSituacaoMatricula(),
                matricula.getDataInicio(),
                matricula.getDataFim(),
                matricula.getDesligamento(),
                fotoAdolescenteMapper.toResponseDTO(matricula.getFoto())
        );
    }

    public MatriculaListarResponseDTO toListarResponseDTO(Matricula matricula) {
        if (matricula == null) return null;

        return new MatriculaListarResponseDTO(
                matricula.getIdMatricula(),
                matricula.getNumMatricula(),
                matricula.getDataInicio(),
                matricula.getDataFim()
        );
    }

    public MatriculaSelectResponseDTO toSelectResponseDTO(Matricula matricula) {
        if (matricula == null) return null;

        return new MatriculaSelectResponseDTO(
                matricula.getIdMatricula(),
                matricula.getNumMatricula(),
                TextoUtils.capitalizar(matricula.getAdolescente().getNome())
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
                TextoUtils.capitalizar(adolescente.getNome()),
                adolescente.getGenero(),
                adolescente.getDataNascimento(),
                TextoUtils.capitalizar(adolescente.getCidadeNascimento()),
                adolescente.getEstadoNascimento(),
                TextoUtils.capitalizar(adolescente.getPaisNascimento()),
                adolescente.getNaturalidade(),
                adolescente.getEstadoCivil(),
                TextoUtils.capitalizar(adolescente.getMae()),
                TextoUtils.capitalizar(adolescente.getPai()),
                TextoUtils.capitalizar(adolescente.getPai()),
                adolescente.getIdadeEm(data),
                adolescente.getSituacaoEm(data)
        );
    }

    public AlunoMatriculaResponseDTO toAlunoResponse(Matricula matricula, LocalDate data) {
        if (matricula == null) return null;
        return new AlunoMatriculaResponseDTO(
                matricula.getAdolescente().getIdAdolescente(),
                matricula.getIdMatricula(),
                matricula.getNumMatricula(),
                TextoUtils.capitalizar(matricula.getAdolescente().getNome()),
                matricula.getAdolescente().getIdadeEm(data),
                matricula.getAdolescente().getSituacaoEm(data)
        );
    }


}
