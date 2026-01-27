package com.projeto.sistema.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.projeto.sistema.utils.EnumComDescricao;
import com.projeto.sistema.utils.EnumUtils;

public enum Situacao implements EnumComDescricao {
    INSCRITO("Inscrito"),
    INSCRITO_INATIVO("Inscrito Inativo"),
    MATRICULADO("Matriculado"),
    MATRICULADO_EM_ESPERA("Em Espera"),
    ESTAGIANDO("Estagiando"),
    DESLIGADO("Desligado"),
    NAO_INICIADO("Não Iniciado");
    private final String descricao;

    Situacao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static Situacao fromDescricao(String descricao) {
        return EnumUtils.fromDescricao(Situacao.class, descricao);
    }
}
