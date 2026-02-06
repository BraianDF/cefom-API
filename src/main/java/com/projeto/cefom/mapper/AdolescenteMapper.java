package com.projeto.cefom.mapper;

import com.projeto.cefom.dto.response.AdolescenteInscricaoResponseDTO;
import com.projeto.cefom.dto.response.AdolescenteListarResponseDTO;
import com.projeto.cefom.dto.response.AdolescenteMatriculaResponseDTO;
import com.projeto.cefom.dto.response.AdolescenteResponseDTO;
import com.projeto.cefom.image.FotoAdolescenteMapper;
import com.projeto.cefom.model.Adolescente;
import com.projeto.cefom.model.Inscricao;
import com.projeto.cefom.model.Matricula;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

@Component
public class AdolescenteMapper {
    private final DocumentoMapper documentoMapper;
    private final EnderecoMapper enderecoMapper;
    private final EscolaridadeMapper escolaridadeMapper;
    private final TelefoneMapper telefoneMapper;
    private final EmailMapper emailMapper;
    private final CaracteristicaMapper caracteristicaMapper;
    private final DadosSocialMapper dadosSocialMapper;
    private final FamiliarMapper familiarMapper;
    private final FotoAdolescenteMapper fotoAdolescenteMapper;
    private final MatriculaMapper matriculaMapper;
    private final InscricaoMapper inscricaoMapper;

    public AdolescenteMapper(DocumentoMapper documentoMapper, EnderecoMapper enderecoMapper, EscolaridadeMapper escolaridadeMapper, TelefoneMapper telefoneMapper, EmailMapper emailMapper, CaracteristicaMapper caracteristicaMapper, DadosSocialMapper dadosSocialMapper, FamiliarMapper familiarMapper, FotoAdolescenteMapper fotoAdolescenteMapper, MatriculaMapper matriculaMapper, InscricaoMapper inscricaoMapper) {
        this.documentoMapper = documentoMapper;
        this.enderecoMapper = enderecoMapper;
        this.escolaridadeMapper = escolaridadeMapper;
        this.telefoneMapper = telefoneMapper;
        this.emailMapper = emailMapper;
        this.caracteristicaMapper = caracteristicaMapper;
        this.dadosSocialMapper = dadosSocialMapper;
        this.familiarMapper = familiarMapper;
        this.fotoAdolescenteMapper = fotoAdolescenteMapper;
        this.matriculaMapper = matriculaMapper;
        this.inscricaoMapper = inscricaoMapper;
    }

    public AdolescenteMatriculaResponseDTO toResponseDTO(Adolescente adolescente, LocalDate data) {
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

    public AdolescenteListarResponseDTO toListarResponseDTO(Adolescente adolescente) {
        return new AdolescenteListarResponseDTO(
                adolescente != null ? adolescente.getIdAdolescente() : null,
                adolescente != null ? adolescente.getNome() : null,
                adolescente != null ? adolescente.getDocumento().getCpf() : null,
                adolescente != null ? adolescente.getSituacao() : null
        );
    }

    public AdolescenteInscricaoResponseDTO toInscricaoResponseDTO(Adolescente adolescente, LocalDate data) {
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

    public AdolescenteResponseDTO toResponseDTO (Adolescente adolescente) {
        if (adolescente == null) return null;
        LocalDate data = LocalDate.now();

        Matricula ultimaMatricula = buscarAtivaOuUltima(
                adolescente.getMatriculas(),
                m -> m.estaValidoEm(data),
                Comparator.comparing((Matricula m) -> m.getDataFim() == null)
                        .thenComparing(Matricula::getDataInicio)
        );
        Inscricao ultimaInscricao = buscarAtivaOuUltima(
                adolescente.getInscricoes(),
                i -> i.estaValidoEm(data),
                Comparator
                        .comparing((Inscricao i) -> i.getDataFim() == null)
                        .thenComparing(Inscricao::getDataInicio)
        );

        return new AdolescenteResponseDTO(
                toResponseDTO(adolescente, data),
                documentoMapper.toResponseDTO(adolescente.getDocumento()),
                enderecoMapper.toResponseDTO(adolescente, data),
                escolaridadeMapper.toResponseDTO(adolescente, data),
                telefoneMapper.toResponseDTO(adolescente, data),
                emailMapper.toResponseDTO(adolescente, data),
                caracteristicaMapper.toResponseDTO(adolescente, data),
                dadosSocialMapper.toResponseDTO(adolescente, data),
                familiarMapper.toResponseDTO(adolescente, data),

                inscricaoMapper.toResponseDTO(ultimaInscricao),
                matriculaMapper.toResponseDTO(ultimaMatricula),

                fotoAdolescenteMapper.toResponseDTO(adolescente, data)
        );
    }

    public static <T> T buscarAtivaOuUltima(
            Collection<T> lista,
            Predicate<T> estaValidoEmData,
            Comparator<T> comparator
    ) {
        if (lista == null || lista.isEmpty()) {
            return null;
        }

        Optional<T> ativa = lista.stream()
                .filter(estaValidoEmData)
                .max(comparator);

        if (ativa.isPresent()) {
            return ativa.get();
        }

        return lista.stream()
                .max(comparator)
                .orElse(null);
    }

}
