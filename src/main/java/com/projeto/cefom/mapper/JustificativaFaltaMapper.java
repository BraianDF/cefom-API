package com.projeto.cefom.mapper;

import com.projeto.cefom.dto.response.JustificativaFaltaListarResponseDTO;
import com.projeto.cefom.dto.response.JustificativaFaltaResponseDTO;
import com.projeto.cefom.model.JustificativaFalta;
import org.springframework.stereotype.Component;

@Component
public class JustificativaFaltaMapper {

    public JustificativaFaltaResponseDTO toResponseDTO(JustificativaFalta justificativa) {
        if (justificativa == null) return null;
        return new JustificativaFaltaResponseDTO(
                justificativa.getIdJustificativaFalta(),
                justificativa.getMotivo(),
                justificativa.getDataInicio(),
                justificativa.getDataFim(),
                justificativa.getQtdeDias(),
                justificativa.getObservacao()
        );
    }

    public JustificativaFaltaListarResponseDTO toListarResponseDTO(JustificativaFalta justificativa) {
        if (justificativa == null) return null;
        return new JustificativaFaltaListarResponseDTO(
                justificativa.getIdJustificativaFalta(),
                justificativa.getDataInicio(),
                justificativa.getDataFim(),
                justificativa.getMotivo()
        );
    }
}
