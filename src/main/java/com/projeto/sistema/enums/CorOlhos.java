package com.projeto.sistema.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.projeto.sistema.utils.EnumComDescricao;
import com.projeto.sistema.utils.EnumUtils;

public enum CorOlhos implements EnumComDescricao {
    AZUL("Azul"),
    CASTANHO("Castanho"),
    PRETO("Preto"),
    VERDE("Verde");
    private final String descricao;

    CorOlhos(String descricao) {
        this.descricao = descricao;
    }

    @Override
    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static CorOlhos fromDescricao(String descricao) {
        return EnumUtils.fromDescricao(CorOlhos.class, descricao);
    }
}
