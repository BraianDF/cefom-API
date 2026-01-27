package com.projeto.sistema.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.projeto.sistema.utils.EnumComDescricao;
import com.projeto.sistema.utils.EnumUtils;

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
