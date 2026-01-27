package com.projeto.sistema.mapper;

import com.projeto.sistema.dto.response.*;
import com.projeto.sistema.model.Adolescente;
import com.projeto.sistema.model.DadosSocial;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.Comparator;

@Component
public class DadosSocialMapper {

    public DadosSocialResponseDTO toResponseDTO(Adolescente adolescente, LocalDate data) {
        DadosSocial dadosSocial = adolescente.getDadosSociais()
                .stream()
                .filter(e -> e.estaValidoEm(data))
                .max(Comparator.comparing(DadosSocial::getDataInicio))
                .orElse(null);

        return toResponseDTO(dadosSocial);
    }

    public DadosSocialResponseDTO toResponseDTO(DadosSocial dadosSocial) {
        return new DadosSocialResponseDTO(
                dadosSocial != null ? dadosSocial.getIdDadosSocial() : null,
                dadosSocial != null ? dadosSocial.getComportamentoBoolean() : null,
                dadosSocial != null ? dadosSocial.getComportamento() : null,
                dadosSocial != null ? dadosSocial.getEncaminhamentoBoolean() : null,
                dadosSocial != null ? dadosSocial.getEncaminhamento() : null,
                dadosSocial != null ? dadosSocial.getBeneficioBoolean() : null,
                dadosSocial != null ? dadosSocial.getBeneficio() : null,
                dadosSocial != null ? dadosSocial.getBeneficioValor() : null,
                dadosSocial != null ? dadosSocial.getProblemaSaudeBoolean() : null,
                dadosSocial != null ? dadosSocial.getProblemaSaude() : null,
                dadosSocial != null ? dadosSocial.getMedicamentoBoolean() : null,
                dadosSocial != null ? dadosSocial.getMedicamento() : null,
                dadosSocial != null ? dadosSocial.getEntidadeBoolean() : null,
                dadosSocial != null ? dadosSocial.getEntidade() : null,
                dadosSocial != null ? dadosSocial.getRendaFamiliar() : null,
                dadosSocial != null ? dadosSocial.getComposicaoFamiliar() : null,
                dadosSocial != null ? dadosSocial.getDataInicio() : null,
                dadosSocial != null ? dadosSocial.getDataFim() : null
        );
    }

    public DadosSocialListarResponseDTO toListarResponseDTO(DadosSocial dadosSocial) {
        return new DadosSocialListarResponseDTO(
                dadosSocial != null ? dadosSocial.getIdDadosSocial() : null,
                dadosSocial != null ? dadosSocial.getDataInicio() : null,
                dadosSocial != null ? dadosSocial.getDataFim() : null,
                dadosSocial != null ? dadosSocial.getEncaminhamentoBoolean() : null,
                dadosSocial != null ? dadosSocial.getBeneficioBoolean() : null
        );
    }
}
