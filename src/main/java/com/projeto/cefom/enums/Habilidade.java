package com.projeto.cefom.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.projeto.cefom.utils.EnumComDescricao;
import com.projeto.cefom.utils.EnumUtils;

public enum Habilidade implements EnumComDescricao {
    AMBIDESTRO("Ambidestro"),
    CANHOTO("Canhoto"),
    DESTRO("Destro");
    private final String descricao;

    Habilidade(String descricao) {
        this.descricao = descricao;
    }

    @Override
    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static Habilidade fromDescricao(String descricao) {
        return EnumUtils.fromDescricao(Habilidade.class, descricao);
    }

}
