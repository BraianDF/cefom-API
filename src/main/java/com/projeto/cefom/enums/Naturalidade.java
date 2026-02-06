package com.projeto.cefom.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.projeto.cefom.utils.EnumComDescricao;
import com.projeto.cefom.utils.EnumUtils;

public enum Naturalidade implements EnumComDescricao {
    BRASILEIRO("Brasileiro(a)"),
    ESTRANGEIRO("Estrangeiro(a)");
    private final String descricao;

    Naturalidade(String descricao) {
        this.descricao = descricao;
    }

    @Override
    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static Naturalidade fromDescricao(String descricao) {
        return EnumUtils.fromDescricao(Naturalidade.class, descricao);
    }
}
