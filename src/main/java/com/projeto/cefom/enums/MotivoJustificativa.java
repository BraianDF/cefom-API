package com.projeto.cefom.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.projeto.cefom.utils.EnumComDescricao;
import com.projeto.cefom.utils.EnumUtils;

public enum MotivoJustificativa implements EnumComDescricao {
    ATESTADO_MEDICA("Atestado Médico", 4),
    DECLARACAO_MEDICA("Declaração Médica", 3),
    DECLARACAO_ESCOLAR("Declaração Escolar", 2),
    COMUNICOU_SEM_DOCUMENTO("Comunicou sem Documento", 1);
    private final String descricao;
    private final int prioridade;

    MotivoJustificativa(String descricao, int prioridade) {
        this.descricao = descricao;
        this.prioridade = prioridade;
    }

    @Override
    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    public int getPrioridade() {
        return prioridade;
    }

    @JsonCreator
    public static MotivoJustificativa fromDescricao(String descricao) {
        return EnumUtils.fromDescricao(MotivoJustificativa.class, descricao);
    }
}
