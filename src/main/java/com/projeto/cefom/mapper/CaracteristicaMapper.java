package com.projeto.cefom.mapper;

import com.projeto.cefom.dto.response.CaracteristicaListarResponseDTO;
import com.projeto.cefom.dto.response.CaracteristicaResponseDTO;
import com.projeto.cefom.model.Adolescente;
import com.projeto.cefom.model.Caracteristica;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.Comparator;

@Component
public class CaracteristicaMapper {

    public CaracteristicaResponseDTO toResponseDTO(Adolescente adolescente, LocalDate data) {
        if (adolescente == null) return null;

        Caracteristica caracteristica = adolescente.getCaracteristicas()
                .stream()
                .filter(e -> e.estaValidoEm(data))
                .max(Comparator.comparing(Caracteristica::getDataInicio))
                .orElse(null);

        return toResponseDTO(caracteristica);
    }

    public CaracteristicaResponseDTO toResponseDTO(Caracteristica caracteristica) {
        if (caracteristica == null) return null;

        return new CaracteristicaResponseDTO(
                caracteristica.getIdCaracteristica(),
                caracteristica.getCorOlhos(),
                caracteristica.getCorCabelo(),
                caracteristica.getCorPele(),
                caracteristica.getAltura(),
                caracteristica.getPeso(),
                caracteristica.getHabilidade(),
                caracteristica.getDataInicio(),
                caracteristica.getDataFim()
        );
    }

    public CaracteristicaListarResponseDTO toListarResponseDTO(Caracteristica caracteristica) {
        if (caracteristica == null) return null;

        return new CaracteristicaListarResponseDTO(
                caracteristica.getIdCaracteristica(),
                caracteristica.getDataInicio(),
                caracteristica.getDataFim()
        );
    }
}
