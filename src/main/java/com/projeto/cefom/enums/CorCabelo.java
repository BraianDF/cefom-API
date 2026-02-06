package com.projeto.cefom.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.projeto.cefom.utils.EnumComDescricao;
import com.projeto.cefom.utils.EnumUtils;

public enum CorCabelo implements EnumComDescricao {
    BRANCO("Branco"),
    CASTANHO("Castanho"),
    GRISALHO("Grisalho"),
    LOIRO("Loiro"),
    PRETO("Preto"),
    RUIVO("Ruivo");
    private final String descricao;

    CorCabelo(String descricao) {
        this.descricao = descricao;
    }

    @Override
    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static CorCabelo fromDescricao(String descricao) {
        return EnumUtils.fromDescricao(CorCabelo.class, descricao);
    }
}
