package com.projeto.cefom.mapper;

import com.projeto.cefom.dto.response.CargoResponseDTO;
import com.projeto.cefom.dto.response.CursoListarResponseDTO;
import com.projeto.cefom.dto.response.CursoResponseDTO;
import com.projeto.cefom.model.Curso;
import com.projeto.cefom.model.VinculoCursoCargo;
import com.projeto.cefom.utils.TextoUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CursoMapper {

    private final CargoMapper cargoMapper;

    public CursoMapper(CargoMapper cargoMapper) {
        this.cargoMapper = cargoMapper;
    }

    public CursoResponseDTO toResponseDTO(Curso curso) {
        if (curso == null) return null;

        List<CargoResponseDTO> cargos = curso.getCargos()
                .stream()
                .map(VinculoCursoCargo::getCargo)
                .distinct() //Evita repetidos
                .map(cargoMapper::toResponseDTO)
                .toList();

        return new CursoResponseDTO(
                curso.getIdCurso(),
                TextoUtils.capitalizar(curso.getNomeCurso()),
                TextoUtils.capitalizar(curso.getNomePrograma()),
                curso.getModalidadeCurso(),
                curso.getProtocoloAprovacao(),
                curso.getCargaHorariaBasica(),
                curso.getCargaHorariaEspecifica(),
                curso.getCargaHorariaTeoricaInicial(),
                curso.getCargaHorariaTeorica(),
                curso.getCargaHorariaPratica(),
                curso.getDataInicio(),
                curso.getDataFim(),
                cargos
        );
    }

    public CursoListarResponseDTO toListarResponseDTO(Curso curso) {
        if (curso == null) return null;

        return new CursoListarResponseDTO(
                curso.getIdCurso(),
                curso.getDataInicio(),
                curso.getDataFim(),
                TextoUtils.capitalizar(curso.getNomeCurso())
        );
    }
}
