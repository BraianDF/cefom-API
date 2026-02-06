package com.projeto.cefom.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.projeto.cefom.utils.EnumComDescricao;
import com.projeto.cefom.utils.EnumUtils;

public enum TipoContratacao implements EnumComDescricao {
    DIRETA("Direta"),
    INDIRETA("Indireta");
    private final String descricao;

    TipoContratacao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static TipoContratacao fromDescricao(String descricao) {
        return EnumUtils.fromDescricao(TipoContratacao.class, descricao);
    }
}
