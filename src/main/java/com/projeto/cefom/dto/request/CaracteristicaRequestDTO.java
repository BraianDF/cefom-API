package com.projeto.cefom.dto.request;

import com.projeto.cefom.enums.CorCabelo;
import com.projeto.cefom.enums.CorOlhos;
import com.projeto.cefom.enums.CorPele;
import com.projeto.cefom.enums.Habilidade;

import java.math.BigDecimal;

public record CaracteristicaRequestDTO(
        CorOlhos corOlhos,
        CorCabelo corCabelo,
        CorPele corPele,
        BigDecimal altura,
        BigDecimal peso,
        Habilidade habilidade
) {
}
