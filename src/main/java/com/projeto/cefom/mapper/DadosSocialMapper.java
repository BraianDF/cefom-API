package com.projeto.cefom.mapper;

import com.projeto.cefom.dto.response.*;
import com.projeto.cefom.model.Adolescente;
import com.projeto.cefom.model.DadosSocial;
import com.projeto.cefom.utils.TextoUtils;
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
                TextoUtils.capitalizarPrimeiraLetra(dadosSocial.getComportamento()),
                dadosSocial.getEncaminhamentoBoolean(),
                TextoUtils.capitalizar(dadosSocial.getEncaminhamento()),
                dadosSocial.getBeneficioBoolean(),
                dadosSocial.getBeneficio(),
                dadosSocial.getBeneficioValor(),
                dadosSocial.getProblemaSaudeBoolean(),
                TextoUtils.capitalizarPrimeiraLetra(dadosSocial.getProblemaSaude()),
                dadosSocial.getMedicamentoBoolean(),
                TextoUtils.capitalizarPrimeiraLetra(dadosSocial.getMedicamento()),
                dadosSocial.getEntidadeBoolean(),
                TextoUtils.capitalizar(dadosSocial.getEntidade()),
                dadosSocial.getRendaFamiliar(),
                TextoUtils.capitalizar(dadosSocial.getComposicaoFamiliar()),
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
