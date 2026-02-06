package com.projeto.cefom.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.projeto.cefom.utils.EnumComDescricao;
import com.projeto.cefom.utils.EnumUtils;

public enum MotivoAfastamento implements EnumComDescricao {
    ACIDENTE_TRABALHO("Acidente Trabalho"),
    DOENCA("Doença"),
    MATERNIDADE("Licença Maternidade"),
    PATERNIDADE("Licença Paternidade"),
    CASAMENTO("Licença Casamento"),
    SERVICO_MILITAR("Serviço Militar"),
    OUTROS("Outros");
    private final String descricao;

    MotivoAfastamento(String descricao) {
        this.descricao = descricao;
    }

    @Override
    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static MotivoAfastamento fromDescricao(String descricao) {
        return EnumUtils.fromDescricao(MotivoAfastamento.class, descricao);
    }
}
