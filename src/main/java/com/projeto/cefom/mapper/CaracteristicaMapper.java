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
        Caracteristica caracteristica = adolescente.getCaracteristicas()
                .stream()
                .filter(e -> e.estaValidoEm(data))
                .max(Comparator.comparing(Caracteristica::getDataInicio))
                .orElse(null);

        return toResponseDTO(caracteristica);
    }

    public CaracteristicaResponseDTO toResponseDTO(Caracteristica caracteristica) {
        return new CaracteristicaResponseDTO(
                caracteristica != null ? caracteristica.getIdCaracteristica() : null,
                caracteristica != null ? caracteristica.getCorOlhos() : null,
                caracteristica != null ? caracteristica.getCorCabelo() : null,
                caracteristica != null ? caracteristica.getCorPele() : null,
                caracteristica != null ? caracteristica.getAltura() : null,
                caracteristica != null ? caracteristica.getPeso() : null,
                caracteristica != null ? caracteristica.getHabilidade() : null,
                caracteristica != null ? caracteristica.getDataInicio() : null,
                caracteristica != null ? caracteristica.getDataFim() : null
        );
    }

    public CaracteristicaListarResponseDTO toListarResponseDTO(Caracteristica caracteristica) {
        return new CaracteristicaListarResponseDTO(
                caracteristica != null ? caracteristica.getIdCaracteristica() : null,
                caracteristica != null ? caracteristica.getDataInicio() : null,
                caracteristica != null ? caracteristica.getDataFim() : null
        );
    }
}
