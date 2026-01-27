package com.projeto.sistema.dto.response;

import java.time.LocalDate;

public record DocumentoAdolescenteResponseDTO(
        Integer idDocumento,
        String cpf,
        String ctps,
        String rg,
        LocalDate dataEmissaoRG,
        String nis,
        String sus,
        String tituloEleitor,
        String zonaTE,
        String secaoTE,
        String cnh,
        String categoriaCNH,
        String reservista
) {
}
