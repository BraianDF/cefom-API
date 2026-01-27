package com.projeto.sistema.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.projeto.sistema.utils.EnumComDescricao;
import com.projeto.sistema.utils.EnumUtils;

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
