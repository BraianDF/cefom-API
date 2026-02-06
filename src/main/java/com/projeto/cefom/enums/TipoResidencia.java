package com.projeto.cefom.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.projeto.cefom.utils.EnumComDescricao;
import com.projeto.cefom.utils.EnumUtils;

public enum TipoResidencia implements EnumComDescricao {
    ALUGADA("Alugada"),
    CEDIDA("Cedida"),
    FINANCIADA("Financiada"),
    PROPRIA("Própria");
    private final String descricao;

    TipoResidencia(String descricao) {
        this.descricao = descricao;
    }

    @Override
    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static TipoResidencia fromDescricao(String descricao) {
        return EnumUtils.fromDescricao(TipoResidencia.class, descricao);
    }
}
