package com.projeto.sistema.mapper;

import com.projeto.sistema.dto.response.AdolescenteEntrevistaResponseDTO;
import com.projeto.sistema.dto.response.EntrevistaListarResponseDTO;
import com.projeto.sistema.dto.response.EntrevistaResponseDTO;
import com.projeto.sistema.model.Matricula;
import com.projeto.sistema.model.Entrevista;
import com.projeto.sistema.model.VinculoEntrevistaMatricula;
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
