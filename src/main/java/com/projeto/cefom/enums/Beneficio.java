package com.projeto.cefom.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.projeto.cefom.utils.EnumComDescricao;
import com.projeto.cefom.utils.EnumUtils;

public enum Beneficio implements EnumComDescricao {
    AUXILIO_DOENCA("Auxílio-Doença"),
    BOLSA_FAMILIA("Bolsa Família"),
    BPC("Benefício de Prestação Continuada"),
    PENSAO_ALIMENTICIA("Pensão Alimentícia"),
    PENSAO_ESPECIAL("Pensão Especial"),
    PENSAO_MORTE("Pensão por Morte"),
    PE_MEIA("Pé de Meia");
    private final String descricao;

    Beneficio(String descricao) {
        this.descricao = descricao;
    }

    @Override
    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static Beneficio fromDescricao(String descricao) {
        return EnumUtils.fromDescricao(Beneficio.class, descricao);
    }
}
