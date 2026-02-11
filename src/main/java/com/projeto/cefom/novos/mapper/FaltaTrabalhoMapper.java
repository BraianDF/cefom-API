package com.projeto.cefom.novos.mapper;

import com.projeto.cefom.novos.dto.response.FaltaTrabalhoListarResponseDTO;
import com.projeto.cefom.novos.dto.response.FaltaTrabalhoResponseDTO;
import com.projeto.cefom.novos.model.FaltaTrabalho;
import org.springframework.stereotype.Component;

@Component
public class FaltaTrabalhoMapper {

    public FaltaTrabalhoResponseDTO toResponseDTO(FaltaTrabalho falta) {
        if (falta == null) return null;
        return new FaltaTrabalhoResponseDTO(
                falta.getIdFaltaTrabalho(),
                falta.getDataInicio(),
                falta.getJustificada()
        );
    }

    public FaltaTrabalhoListarResponseDTO toListarResponseDTO(FaltaTrabalho falta) {
        if (falta == null) return null;
        return new FaltaTrabalhoListarResponseDTO(
                falta.getIdFaltaTrabalho(),
                falta.getDataInicio(),
                falta.getJustificada()
        );
    }
}
