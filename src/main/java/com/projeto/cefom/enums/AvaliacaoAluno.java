package com.projeto.cefom.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.projeto.cefom.exceptions.RegraNegocioException;
import com.projeto.cefom.utils.EnumComDescricao;
import com.projeto.cefom.utils.EnumUtils;

public enum AvaliacaoAluno implements EnumComDescricao {
    VERDE("Verde", 3),
    AMARELO("Amarelo", 2),
    VERMELHO("Vermelho", 1),
    BRANCO("Branco", 0),
    PRETO("Preto", 0);
    private final String descricao;
    private final int codigo;

    AvaliacaoAluno(String descricao, int codigo) {
        this.descricao = descricao;
        this.codigo = codigo;
    }

    @Override
    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    @JsonCreator
    public static AvaliacaoAluno fromDescricao(String descricao) {
        return EnumUtils.fromDescricao(AvaliacaoAluno.class, descricao);
    }

    public static AvaliacaoAluno fromCodigo(int codigo, boolean presente) {
        if (codigo == 0) {
            return presente ? BRANCO : PRETO;
        }

        return switch (codigo) {
            case 1 -> VERMELHO;
            case 2 -> AMARELO;
            case 3 -> VERDE;
            default -> throw new RegraNegocioException("Código de avaliação inválido.");
        };
    }
}
