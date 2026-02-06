package com.projeto.cefom.dto.response;

import com.projeto.cefom.enums.CorCabelo;
import com.projeto.cefom.enums.CorOlhos;
import com.projeto.cefom.enums.CorPele;
import com.projeto.cefom.enums.Habilidade;

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
