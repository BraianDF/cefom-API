package com.projeto.cefom.mapper;

import com.projeto.cefom.dto.response.TerritorioListarResponseDTO;
import com.projeto.cefom.dto.response.TerritorioResponseDTO;
import com.projeto.cefom.model.Territorio;
import org.springframework.stereotype.Component;

@Component
public class TerritorioMapper {

    public TerritorioResponseDTO toResponseDTO(Territorio territorio) {
        return new TerritorioResponseDTO(
                territorio.getIdTerritorio(),
                territorio.getResultado(),
                territorio.getBairros()
        );
    }

    public TerritorioListarResponseDTO toListarResponseDTO(Territorio territorio) {
        return new TerritorioListarResponseDTO(
                territorio.getIdTerritorio(),
                territorio.getResultado()
        );
    }
}
