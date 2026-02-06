package com.projeto.cefom.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.projeto.cefom.utils.EnumComDescricao;
import com.projeto.cefom.utils.EnumUtils;

public enum TipoEscola implements EnumComDescricao {
    PUBLICA("Pública"),
    PARTICULAR("Particular");
    private final String descricao;

    TipoEscola(String descricao) {
        this.descricao = descricao;
    }

    @Override
    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static TipoEscola fromDescricao(String descricao) {
        return EnumUtils.fromDescricao(TipoEscola.class, descricao);
    }
}
