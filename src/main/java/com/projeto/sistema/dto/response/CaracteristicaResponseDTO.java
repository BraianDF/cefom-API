package com.projeto.sistema.dto.response;

import com.projeto.sistema.enums.CorCabelo;
import com.projeto.sistema.enums.CorOlhos;
import com.projeto.sistema.enums.CorPele;
import com.projeto.sistema.enums.Habilidade;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CaracteristicaResponseDTO(
        Integer idCaracteristica,
        CorOlhos corOlhos,
        CorCabelo corCabelo,
        CorPele corPele,
        BigDecimal altura,
        BigDecimal peso,
        Habilidade habilidade,
        LocalDate dataInicio,
        LocalDate dataFim
) {
}
