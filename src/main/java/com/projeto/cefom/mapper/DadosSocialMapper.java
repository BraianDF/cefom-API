package com.projeto.cefom.mapper;

import com.projeto.cefom.dto.response.*;
import com.projeto.cefom.model.Adolescente;
import com.projeto.cefom.model.DadosSocial;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.Comparator;

@Component
public class DadosSocialMapper {

    public DadosSocialResponseDTO toResponseDTO(Adolescente adolescente, LocalDate data) {
        if (adolescente == null) return null;

        DadosSocial dadosSocial = adolescente.getDadosSociais()
                .stream()
                .filter(e -> e.estaValidoEm(data))
                .max(Comparator.comparing(DadosSocial::getDataInicio))
                .orElse(null);

        return toResponseDTO(dadosSocial);
    }

    public DadosSocialResponseDTO toResponseDTO(DadosSocial dadosSocial) {
        if (dadosSocial == null) return null;

        return new DadosSocialResponseDTO(
                dadosSocial.getIdDadosSocial(),
                dadosSocial.getComportamentoBoolean(),
                dadosSocial.getComportamento(),
                dadosSocial.getEncaminhamentoBoolean(),
                dadosSocial.getEncaminhamento(),
                dadosSocial.getBeneficioBoolean(),
                dadosSocial.getBeneficio(),
                dadosSocial.getBeneficioValor(),
                dadosSocial.getProblemaSaudeBoolean(),
                dadosSocial.getProblemaSaude(),
                dadosSocial.getMedicamentoBoolean(),
                dadosSocial.getMedicamento(),
                dadosSocial.getEntidadeBoolean(),
                dadosSocial.getEntidade(),
                dadosSocial.getRendaFamiliar(),
                dadosSocial.getComposicaoFamiliar(),
                dadosSocial.getDataInicio(),
                dadosSocial.getDataFim()
        );
    }

    public DadosSocialListarResponseDTO toListarResponseDTO(DadosSocial dadosSocial) {
        if (dadosSocial == null) return null;

        return new DadosSocialListarResponseDTO(
                dadosSocial.getIdDadosSocial(),
                dadosSocial.getDataInicio(),
                dadosSocial.getDataFim(),
                dadosSocial.getEncaminhamentoBoolean(),
                dadosSocial.getBeneficioBoolean()
        );
    }
}
