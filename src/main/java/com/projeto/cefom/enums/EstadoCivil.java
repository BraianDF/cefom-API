package com.projeto.cefom.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.projeto.cefom.utils.EnumComDescricao;
import com.projeto.cefom.utils.EnumUtils;

public enum EstadoCivil implements EnumComDescricao {
    SOLTEIRO("Solteiro(a)"),
    CASADO("Casado(a)"),
    VIUVO("Viuvo(a)"),
    DIVORCIADO("Divorciado(a)"),
    OUTRO("Outro"),
    IGNORADO("Ignorado(a)"),
    UNIAO_ESTAVEL("União Estavel");

    private final String descricao;

    EstadoCivil(String descricao) {
        this.descricao = descricao;
    }

    @Override
    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static EstadoCivil fromDescricao(String descricao) {
        return EnumUtils.fromDescricao(EstadoCivil.class, descricao);
    }
}
