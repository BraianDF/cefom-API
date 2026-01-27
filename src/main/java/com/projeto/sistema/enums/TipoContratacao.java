package com.projeto.sistema.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.projeto.sistema.utils.EnumComDescricao;
import com.projeto.sistema.utils.EnumUtils;

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
