package com.projeto.sistema.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.projeto.sistema.utils.EnumComDescricao;
import com.projeto.sistema.utils.EnumUtils;

public enum ModalidadeCurso implements EnumComDescricao {
    PRESENCIAL("Presencial"),
    DISTANCIA("A Distância"),
    HIBRIDO("Híbrido");
    private final String descricao;

    ModalidadeCurso(String descricao) {
        this.descricao = descricao;
    }

    @Override
    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static ModalidadeCurso fromDescricao(String descricao) {
        return EnumUtils.fromDescricao(ModalidadeCurso.class, descricao);
    }

}
