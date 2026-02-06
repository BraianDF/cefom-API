package com.projeto.cefom.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.projeto.cefom.utils.EnumComDescricao;
import com.projeto.cefom.utils.EnumUtils;

public enum EscolaridadeFamiliar implements EnumComDescricao {
    SEM("Sem Escolariedade"),
    CEF("Cursando Ensino Fundamental"),
    EFI("Ensino Fundamental Incompleto"),
    EFC("Ensino Fundamental Completo"),
    CEM("Cursando Ensino Médio"),
    EMI("Ensino Médio Incompleto"),
    EMC("Ensino Médio Completo"),
    CET("Cursando Ensino Técnico"),
    ETI("Ensino Técnico Incompleto"),
    ETC("Ensino Técnico Completo"),
    CES("Cursando Ensino Superior"),
    ESI("Ensino Superior Incompleto"),
    ESC("Ensino Superior Completo");
    private final String descricao;

    EscolaridadeFamiliar(String descricao) {
        this.descricao = descricao;
    }

    @Override
    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static EscolaridadeFamiliar fromDescricao(String descricao) {
        return EnumUtils.fromDescricao(EscolaridadeFamiliar.class, descricao);
    }
}
