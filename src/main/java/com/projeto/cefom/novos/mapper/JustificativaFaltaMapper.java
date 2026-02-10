package com.projeto.cefom.novos.mapper;

import com.projeto.cefom.novos.dto.response.*;
import com.projeto.cefom.novos.model.JustificativaFalta;
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
