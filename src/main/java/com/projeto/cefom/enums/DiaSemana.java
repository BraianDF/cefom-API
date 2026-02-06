package com.projeto.cefom.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.projeto.cefom.utils.EnumComDescricao;
import com.projeto.cefom.utils.EnumUtils;

public enum DiaSemana implements EnumComDescricao {
    DOMINGO("Domingo"),
    SEGUNDA_FEIRA("Segunda-Feira"),
    TERCA_FEIRA("Terça-Feira"),
    QUARTA_FEIRA("Quarta-Feira"),
    QUINTA_FEIRA("Quinta-Feira"),
    SEXTA_FEIRA("Sexta-Feira"),
    SABADO("Sábado");
    private final String descricao;

    DiaSemana(String descricao) {
        this.descricao = descricao;
    }

    @Override
    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static DiaSemana fromDescricao(String descricao) {
        return EnumUtils.fromDescricao(DiaSemana.class, descricao);
    }
}
