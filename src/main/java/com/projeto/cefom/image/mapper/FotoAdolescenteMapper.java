package com.projeto.cefom.image.mapper;

import com.projeto.cefom.enums.Situacao;
import com.projeto.cefom.image.dto.UploadFotoAdolescenteResponseDTO;
import com.projeto.cefom.image.model.FotoAdolescente;
import com.projeto.cefom.model.Adolescente;
import com.projeto.cefom.model.Inscricao;
import com.projeto.cefom.model.Matricula;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.Predicate;

@Component
public class FotoAdolescenteMapper {

    private static final UploadFotoAdolescenteResponseDTO semFoto = new UploadFotoAdolescenteResponseDTO(null,"SemFoto.jpg","image/jpeg",11461,"http://localhost:8081/sistema/files/download/SemFoto.jpg");

    public UploadFotoAdolescenteResponseDTO toResponseDTO(Adolescente adolescente, LocalDate data) {
        if (adolescente == null) return null;

        Situacao situacao = adolescente.getSituacaoEm(data);

        Matricula ultimaMatricula = buscarAtivaOuUltima(
                adolescente.getMatriculas(),
                m -> m.estaValidoEm(data),
                Comparator.comparing(Matricula::getDataInicio)
        );

        Inscricao ultimaInscricao = buscarAtivaOuUltima(
                adolescente.getInscricoes(),
                i -> i.estaValidoEm(data),
                Comparator.comparing(Inscricao::getDataInicio)
        );

        return switch (situacao) {
            case INSCRITO, INSCRITO_INATIVO ->
                    fotoOuPadrao(ultimaInscricao == null ? null : ultimaInscricao.getFoto());

            case MATRICULADO, MATRICULADO_EM_ESPERA, ESTAGIANDO, DESLIGADO ->
                    fotoOuPadrao(ultimaMatricula == null ? null : ultimaMatricula.getFoto());

            default -> semFoto;
        };
    }

    public UploadFotoAdolescenteResponseDTO toResponseDTO(Matricula matricula) {
        if (matricula == null) return null;

        FotoAdolescente foto = matricula.getFoto();

        return toResponseDTO(foto);
    }

    public UploadFotoAdolescenteResponseDTO toResponseDTO(Inscricao inscricao) {
        if (inscricao == null) return null;

        FotoAdolescente foto = inscricao.getFoto();

        return toResponseDTO(foto);
    }

    public UploadFotoAdolescenteResponseDTO toResponseDTO(FotoAdolescente foto) {
        if (foto == null) return semFoto;
        return new UploadFotoAdolescenteResponseDTO(
                foto.getIdFotoAdolescente(),
                foto.getNomeArquivo(),
                foto.getTipoArquivo(),
                foto.getTamanhoArquivo(),
                foto.getCaminhoArquivo()
        );
    }

    private UploadFotoAdolescenteResponseDTO fotoOuPadrao(FotoAdolescente foto) {
        return foto == null ? semFoto : toResponseDTO(foto);
    }

    public static <T> T buscarAtivaOuUltima(
            Collection<T> lista,
            Predicate<T> estaValidoEmData,
            Comparator<T> comparator
    ) {
        return lista.stream()
                .filter(estaValidoEmData)
                .max(comparator)
                .orElseGet(() ->
                        lista.stream()
                                .max(comparator)
                                .orElse(null)
                );
    }
}
