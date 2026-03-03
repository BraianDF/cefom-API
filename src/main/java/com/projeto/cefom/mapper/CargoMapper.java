package com.projeto.cefom.mapper;

import com.projeto.cefom.dto.response.CargoListarResponseDTO;
import com.projeto.cefom.dto.response.CargoResponseDTO;
import com.projeto.cefom.dto.response.CargoSelectResponseDTO;
import com.projeto.cefom.model.Cargo;
import com.projeto.cefom.model.Contrato;
import com.projeto.cefom.model.VinculoContratoCargo;
import com.projeto.cefom.utils.TextoUtils;
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
                TextoUtils.capitalizar(cargo.getFuncao())
        );
    }

    public CargoListarResponseDTO toListarResponseDTO(Cargo cargo) {
        if (cargo == null) return null;

        return new CargoListarResponseDTO(
                cargo.getIdCargo(),
                TextoUtils.capitalizar(cargo.getFuncao())
        );
    }

    public CargoSelectResponseDTO toSelectResponseDTO(Cargo cargo) {
        if (cargo == null) return null;

        return new CargoSelectResponseDTO(
                cargo.getIdCargo(),
                TextoUtils.capitalizar(cargo.getFuncao())
        );
    }
}
