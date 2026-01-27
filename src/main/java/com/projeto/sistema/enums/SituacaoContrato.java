package com.projeto.sistema.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.projeto.sistema.utils.EnumComDescricao;
import com.projeto.sistema.utils.EnumUtils;

public enum SituacaoContrato implements EnumComDescricao {
    CONCLUIDO("Concluído"),
    MATRICULADO("Matriculado"),
    AFASTADO_SUSPENSO("Afastado/Suspenso"),
    RESCINDIDO("Rescindido"),
    NAO_INICIADO("Não Iniciado");
    private final String descricao;

    SituacaoContrato(String descricao) {
        this.descricao = descricao;
    }

    @Override
    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static SituacaoContrato fromDescricao(String descricao) {
        return EnumUtils.fromDescricao(SituacaoContrato.class, descricao);
    }

}
