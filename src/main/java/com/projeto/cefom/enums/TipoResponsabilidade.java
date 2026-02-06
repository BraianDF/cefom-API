package com.projeto.cefom.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.projeto.cefom.utils.EnumComDescricao;
import com.projeto.cefom.utils.EnumUtils;

public enum TipoResponsabilidade implements EnumComDescricao {
    EMPRESA("Empresa"),
    APRENDIZES("Aprendizes"),
    ENTREVISTAS("Entrevistas");
    private final String descricao;

    TipoResponsabilidade(String descricao) {
        this.descricao = descricao;
    }

    @Override
    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static TipoResponsabilidade fromDescricao(String descricao) {
        return EnumUtils.fromDescricao(TipoResponsabilidade.class, descricao);
    }
}
