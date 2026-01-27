package com.projeto.sistema.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.projeto.sistema.utils.EnumComDescricao;
import com.projeto.sistema.utils.EnumUtils;

public enum SituacaoMatricula implements EnumComDescricao {
    CONVOCADO("Convocado(a)"),
    ENCAMINHADO("Encaminhado(a)");
    private final String descricao;

    SituacaoMatricula(String descricao) {
        this.descricao = descricao;
    }

    @Override
    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static SituacaoMatricula fromDescricao(String descricao) {
        return EnumUtils.fromDescricao(SituacaoMatricula.class, descricao);
    }
}
