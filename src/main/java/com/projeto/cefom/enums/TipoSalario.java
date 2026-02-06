package com.projeto.cefom.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.projeto.cefom.utils.EnumComDescricao;
import com.projeto.cefom.utils.EnumUtils;

public enum TipoSalario implements EnumComDescricao {
    HORA("Hora"),
    DIA("Dia"),
    MES("Mês");
    private final String descricao;

    TipoSalario(String descricao) {
        this.descricao = descricao;
    }

    @Override
    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static TipoSalario fromDescricao(String descricao) {
        return EnumUtils.fromDescricao(TipoSalario.class, descricao);
    }
}
