package com.projeto.cefom.mapper;

import com.projeto.cefom.dto.response.JornadaTrabalhoListarResponseDTO;
import com.projeto.cefom.dto.response.JornadaTrabalhoResponseDTO;
import com.projeto.cefom.model.Contrato;
import com.projeto.cefom.model.JornadaTrabalho;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Comparator;

@Component
public class JornadaTrabalhoMapper {

    public JornadaTrabalhoResponseDTO toResponseDTO (Contrato contrato, LocalDate data) {
        JornadaTrabalho jornadaTrabalho = contrato.getJornadasTrabalho()
                .stream()
                .filter(e -> e.estaValidoEm(data))
                .max(Comparator.comparing(JornadaTrabalho::getDataInicio))
                .orElse(null);

        return toResponseDTO(jornadaTrabalho);
    }

    public JornadaTrabalhoResponseDTO toResponseDTO(JornadaTrabalho jornadaTrabalho) {
        if (jornadaTrabalho == null) return null;

        return new JornadaTrabalhoResponseDTO(
                jornadaTrabalho.getIdJornadaTrabalho(),
                jornadaTrabalho.getCargaHoraria(),
                jornadaTrabalho.getHorarioSemanaEntrada(),
                jornadaTrabalho.getHorarioSemanaSaida(),
                jornadaTrabalho.getHorarioAlmocoEntrada(),
                jornadaTrabalho.getHorarioAlmocoSaida(),
                jornadaTrabalho.getHorarioSabadoEntrada(),
                jornadaTrabalho.getHorarioSabadoSaida(),
                jornadaTrabalho.getDiaCurso(),
                jornadaTrabalho.getDiaFolga(),
                jornadaTrabalho.getDataInicio(),
                jornadaTrabalho.getDataFim()
        );
    }

    public JornadaTrabalhoListarResponseDTO toListarResponseDTO(JornadaTrabalho jornadaTrabalho) {
        if (jornadaTrabalho == null) return null;

        return new JornadaTrabalhoListarResponseDTO(
                jornadaTrabalho.getIdJornadaTrabalho(),
                jornadaTrabalho.getDataInicio(),
                jornadaTrabalho.getDataFim()
        );
    }
}
