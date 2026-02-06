package com.projeto.cefom.mapper;

import com.projeto.cefom.dto.response.CargoResponseDTO;
import com.projeto.cefom.dto.response.CursoListarResponseDTO;
import com.projeto.cefom.dto.response.CursoResponseDTO;
import com.projeto.cefom.model.Curso;
import com.projeto.cefom.model.VinculoCursoCargo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CursoMapper {

    private final CargoMapper cargoMapper;

    public CursoMapper(CargoMapper cargoMapper) {
        this.cargoMapper = cargoMapper;
    }

    public CursoResponseDTO toResponseDTO(Curso curso) {
        List<CargoResponseDTO> cargos = curso != null ?
                curso.getCargos()
                .stream()
                .map(VinculoCursoCargo::getCargo)
                .distinct() //Evita repetidos
                .map(cargoMapper::toResponseDTO)
                .toList()
        : null;

        return new CursoResponseDTO(
                curso != null ? curso.getIdCurso() : null,
                curso != null ? curso.getNomeCurso() : null,
                curso != null ? curso.getNomePrograma() : null,
                curso != null ? curso.getModalidadeCurso() : null,
                curso != null ? curso.getProtocoloAprovacao() : null,
                curso != null ? curso.getCargaHorariaBasica() : null,
                curso != null ? curso.getCargaHorariaEspecifica() : null,
                curso != null ? curso.getCargaHorariaTeoricaInicial() : null,
                curso != null ? curso.getCargaHorariaTeorica() : null,
                curso != null ? curso.getCargaHorariaPratica() : null,
                curso != null ? curso.getDataInicio() : null,
                curso != null ? curso.getDataFim() : null,
                cargos
        );
    }

    public CursoListarResponseDTO toListarResponseDTO(Curso curso) {
        return new CursoListarResponseDTO(
                curso != null ? curso.getIdCurso() : null,
                curso != null ? curso.getDataInicio() : null,
                curso != null ? curso.getDataFim() : null,
                curso != null ? curso.getNomeCurso() : null
        );
    }
}
