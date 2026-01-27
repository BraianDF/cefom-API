package com.projeto.sistema.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.projeto.sistema.utils.EnumComDescricao;
import com.projeto.sistema.utils.EnumUtils;

public enum Genero implements EnumComDescricao {
    FEMININO("Feminino"),
    MASCULINO("Masculino"),
    OUTRO("Outro");

    private final String descricao;

    Genero(String descricao) {
        this.descricao = descricao;
    }

    @Override
    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static Genero fromDescricao(String descricao) {
        return EnumUtils.fromDescricao(Genero.class, descricao);
    }

}
