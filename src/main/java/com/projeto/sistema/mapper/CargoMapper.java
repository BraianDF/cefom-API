package com.projeto.sistema.mapper;

import com.projeto.sistema.dto.response.CargoListarResponseDTO;
import com.projeto.sistema.dto.response.CargoResponseDTO;
import com.projeto.sistema.model.Cargo;
import com.projeto.sistema.model.Contrato;
import com.projeto.sistema.model.VinculoContratoCargo;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Comparator;

@Component
public class CargoMapper {

    public CargoResponseDTO toResponseDTO(Contrato contrato, LocalDate data) {
        VinculoContratoCargo vinculo = contrato.getCargos()
                .stream()
                .filter(e -> e.estaValidoEm(data))
                .max(Comparator.comparing(VinculoContratoCargo::getDataInicio))
                .orElse(null);

        return toResponseDTO(vinculo.getCargo());
    }

    public CargoResponseDTO toResponseDTO(Cargo cargo) {
        if (cargo == null) return null;

        return new CargoResponseDTO(
                cargo.getIdCargo(),
                cargo.getCbo(),
                cargo.getFuncao()
        );
    }

    public CargoListarResponseDTO toListarResponseDTO(Cargo cargo) {
        if (cargo == null) return null;

        return new CargoListarResponseDTO(
                cargo.getIdCargo(),
                cargo.getFuncao()
        );
    }
}
