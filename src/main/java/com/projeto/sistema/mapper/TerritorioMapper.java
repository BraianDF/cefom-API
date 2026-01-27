package com.projeto.sistema.mapper;

import com.projeto.sistema.dto.response.TerritorioListarResponseDTO;
import com.projeto.sistema.dto.response.TerritorioResponseDTO;
import com.projeto.sistema.model.Territorio;
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
