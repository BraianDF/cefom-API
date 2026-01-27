package com.projeto.sistema.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.projeto.sistema.utils.EnumComDescricao;
import com.projeto.sistema.utils.EnumUtils;

public enum CorPele implements EnumComDescricao {
    AMARELA("Amarela"),
    BRANCA("Branca"),
    INDIGENA("Indigena"),
    PARDA("Parda"),
    Preta("Preta"),
    NAO_INFORMADO("Não Informado");
    private final String descricao;

    CorPele(String descricao) {
        this.descricao = descricao;
    }

    @Override
    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static CorPele fromDescricao(String descricao) {
        return EnumUtils.fromDescricao(CorPele.class, descricao);
    }
}
