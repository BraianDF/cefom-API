package com.projeto.sistema.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.projeto.sistema.utils.EnumComDescricao;
import com.projeto.sistema.utils.EnumUtils;

public enum FinalizacaoInscricao implements EnumComDescricao {
    ABANDONO_ESCOLAR("Abandono Escolar"),
    CONVOCACAO_PARA_ENTIDADE("Convocado Para a Entidade"),
    DESISTENCIA_VOLUNTARIA("Desistência Voluntária"),
    ESCOLARIDADE_INCOMPATIVEL("Escolaridade Incompatível"),
    FALECIMENTO("Falecimento"),
    IDADE_INCOMPATIVEL("Idade Incompatível"),
    INGRESSO_OUTRA_ENTIDADE("Ingresso em Outra Entidade"),
    MUDANCA_MUNICIPIO("Mudança de Município"),
    NAO_COMPARECIMENTO_APOS_CONVOCACAO("Não Comparecimento Após Convocação"),
    OBTENCAO_EMPREGO_OU_ESTAGIO("Obtenção de Emprego ou Estágio"),
    TENTATIVAS_CONTATO_SEM_SUCESSO("Tentativas de Contato sem Sucesso");

    private final String descricao;

    FinalizacaoInscricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static FinalizacaoInscricao fromDescricao(String descricao) {
        return EnumUtils.fromDescricao(FinalizacaoInscricao.class, descricao);
    }
}
