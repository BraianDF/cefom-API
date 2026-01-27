package com.projeto.sistema.dto.response;

import com.projeto.sistema.enums.Estado;
import com.projeto.sistema.enums.TipoResidencia;

import java.time.LocalDate;

public record EnderecoMatriculaResponseDTO(
        Integer idEndereco,
        String cep,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        Estado estado,
        TipoResidencia tipoResidencia,
        String territorio,
        LocalDate dataInicio,
        LocalDate dataFim
) {
}
