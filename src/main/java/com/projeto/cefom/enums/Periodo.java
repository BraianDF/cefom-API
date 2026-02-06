package com.projeto.cefom.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.projeto.cefom.utils.EnumComDescricao;
import com.projeto.cefom.utils.EnumUtils;

public enum Periodo implements EnumComDescricao {
    EAD ("EAD"),
    INTEGRAL ("Integral"),
    MANHA ("Manhã"),
    NOITE ("Noite"),
    TARDE ("Tarde");
    private final String descricao;

    Periodo(String descricao) {
        this.descricao = descricao;
    }

    @Override
    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static Periodo fromDescricao(String descricao) {
        return EnumUtils.fromDescricao(Periodo.class, descricao);
    }
}
