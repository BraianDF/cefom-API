package com.projeto.sistema.dto.request;

import com.projeto.sistema.enums.CorCabelo;
import com.projeto.sistema.enums.CorOlhos;
import com.projeto.sistema.enums.CorPele;
import com.projeto.sistema.enums.Habilidade;

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
