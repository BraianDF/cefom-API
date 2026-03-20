package com.projeto.cefom.mapper;

import com.projeto.cefom.dto.response.EscolaListarResponseDTO;
import com.projeto.cefom.dto.response.EscolaResponseDTO;
import com.projeto.cefom.dto.response.EscolaSelectResponseDTO;
import com.projeto.cefom.model.Escola;
import com.projeto.cefom.utils.TextoUtils;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class EscolaMapper {

    private final EnderecoMapper enderecoMapper;

    public EscolaMapper(EnderecoMapper enderecoMapper) {
        this.enderecoMapper = enderecoMapper;
    }

    public EscolaResponseDTO toResponseDTO(Escola escola, LocalDate data) {
        if (escola == null) return null;

        return new EscolaResponseDTO(
                escola.getIdEscola(),
                TextoUtils.capitalizar(escola.getNome()),
                escola.getTipo(),
                enderecoMapper.toResponseDTO(escola, data)
        );
    }

    public EscolaListarResponseDTO toListarResponseDTO(Escola escola) {
        if (escola == null) return null;

        return new EscolaListarResponseDTO(
                escola.getIdEscola(),
                TextoUtils.capitalizar(escola.getNome())
        );
    }

    public EscolaSelectResponseDTO toSelectResponseDTO(Escola escola) {
        if (escola == null) return null;

        return new EscolaSelectResponseDTO(
                escola.getIdEscola(),
                TextoUtils.capitalizar(escola.getNome())
        );
    }
}
