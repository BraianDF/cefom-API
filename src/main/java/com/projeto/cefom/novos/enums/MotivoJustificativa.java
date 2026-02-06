package com.projeto.cefom.novos.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.projeto.cefom.utils.EnumComDescricao;
import com.projeto.cefom.utils.EnumUtils;

public enum MotivoJustificativa implements EnumComDescricao {
    ATESTADO_MEDICA("Atestado Médico"),
    DECLARACAO_MEDICA("Declaração Médica"),
    DECLARACAO_ESCOLAR("Declaração Escolar"),
    COMUNICOU_SEM_DOCUMENTO("Comunicou sem Documento");
    private final String descricao;

    MotivoJustificativa(String descricao) {
        this.descricao = descricao;
    }

    @Override
    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static MotivoJustificativa fromDescricao(String descricao) {
        return EnumUtils.fromDescricao(MotivoJustificativa.class, descricao);
    }
}
