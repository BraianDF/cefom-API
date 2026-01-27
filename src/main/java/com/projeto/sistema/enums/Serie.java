package com.projeto.sistema.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.projeto.sistema.utils.EnumComDescricao;
import com.projeto.sistema.utils.EnumUtils;

public enum Serie implements EnumComDescricao {
    ANO_1 ("1º Ano"),
    ANO_2 ("2º Ano"),
    ANO_3 ("3º Ano"),
    ANO_4 ("4º Ano"),
    ANO_5 ("5º Ano"),
    ANO_6 ("6º Ano"),
    ANO_7 ("7º Ano"),
    ANO_8 ("8º Ano"),
    ANO_9 ("9º Ano"),
    SERIE_1 ("1º Série"),
    SERIE_2 ("2º Série"),
    SERIE_3 ("3º Série"),
    MODULO_1 ("1º Módulo"),
    MODULO_2 ("2º Módulo"),
    MODULO_3 ("3º Módulo"),
    MODULO_4 ("4º Módulo"),
    MODULO_5 ("5º Módulo"),
    MODULO_6 ("6º Módulo"),
    SEMESTRE_1 ("1º Semestre"),
    SEMESTRE_2 ("2º Semestre"),
    SEMESTRE_3 ("3º Semestre"),
    SEMESTRE_4 ("4º Semestre"),
    SEMESTRE_5 ("5º Semestre"),
    SEMESTRE_6 ("6º Semestre"),
    SEMESTRE_7 ("7º Semestre"),
    SEMESTRE_8 ("8º Semestre"),
    SEMESTRE_9 ("9º Semestre"),
    SEMESTRE_10 ("10º Semestre"),
    SEMESTRE_11 ("11º Semestre"),
    SEMESTRE_12 ("12º Semestre"),
    SEMESTRE_13 ("12º Semestre");
    private final String descricao;

    Serie(String descricao) {
        this.descricao = descricao;
    }

    @Override
    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static Serie fromDescricao(String descricao) {
        return EnumUtils.fromDescricao(Serie.class, descricao);
    }

}
