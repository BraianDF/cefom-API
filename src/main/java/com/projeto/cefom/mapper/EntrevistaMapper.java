package com.projeto.cefom.mapper;

import com.projeto.cefom.dto.response.AdolescenteEntrevistaResponseDTO;
import com.projeto.cefom.dto.response.EntrevistaListarResponseDTO;
import com.projeto.cefom.dto.response.EntrevistaResponseDTO;
import com.projeto.cefom.model.Matricula;
import com.projeto.cefom.model.Entrevista;
import com.projeto.cefom.model.VinculoEntrevistaMatricula;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class EntrevistaMapper {

    private final EmpresaMapper empresaMapper;
    private final MatriculaMapper matriculaMapper;

    public EntrevistaMapper(EmpresaMapper empresaMapper, MatriculaMapper matriculaMapper) {
        this.empresaMapper = empresaMapper;
        this.matriculaMapper = matriculaMapper;
    }

    public EntrevistaResponseDTO toResponseDTO(Entrevista entrevista) {
        if (entrevista == null) return null;

        List<AdolescenteEntrevistaResponseDTO> adolescentes = entrevista.getAdolescentes()
                .stream()
                .map(VinculoEntrevistaMatricula::getMatricula)
                .distinct() //Evita repetidos
                .map((Matricula matricula) -> matriculaMapper.toEntrevistaResponseDTO(matricula, entrevista))
                .toList();

        return new EntrevistaResponseDTO(
                entrevista.getIdEntrevista(),
                entrevista.getDataInicio(),
                entrevista.getEntrevistaCancelada(),
                empresaMapper.toResponseDTO(entrevista),
                adolescentes
        );
    }

    public EntrevistaListarResponseDTO toListarResponseDTO(Entrevista entrevista) {
        if (entrevista == null) return null;

        return new EntrevistaListarResponseDTO(
                entrevista.getIdEntrevista(),
                entrevista.getDataInicio(),
                empresaMapper.toListarResponseDTO(entrevista.getEmpresa())
        );
    }
}
