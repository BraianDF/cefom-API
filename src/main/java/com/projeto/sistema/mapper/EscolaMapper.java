package com.projeto.sistema.mapper;

import com.projeto.sistema.dto.response.EscolaListarResponseDTO;
import com.projeto.sistema.dto.response.EscolaResponseDTO;
import com.projeto.sistema.dto.response.EscolaSelectResponseDTO;
import com.projeto.sistema.model.Escola;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class EscolaMapper {

    private final EnderecoMapper enderecoMapper;

    public EscolaMapper(EnderecoMapper enderecoMapper) {
        this.enderecoMapper = enderecoMapper;
    }

    public EscolaResponseDTO toResponseDTO(Escola escola, LocalDate data) {
        return new EscolaResponseDTO(
                escola != null ? escola.getIdEscola() : null,
                escola != null ? escola.getNome() : null,
                escola != null ? escola.getTipo() : null,
                escola != null ? enderecoMapper.toResponseDTO(escola, data) : null
        );
    }

    public EscolaListarResponseDTO toListarResponseDTO(Escola escola) {
        if (escola == null) return null;

        return new EscolaListarResponseDTO(
                escola.getIdEscola(),
                escola.getNome()
        );
    }

    public EscolaSelectResponseDTO toSelectResponseDTO(Escola escola) {
        if (escola == null) return null;

        return new EscolaSelectResponseDTO(
                escola.getIdEscola(),
                escola.getNome()
        );
    }
}
