package com.projeto.cefom.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.projeto.cefom.utils.EnumComDescricao;
import com.projeto.cefom.utils.EnumUtils;

public enum DesligamentoMatricula implements EnumComDescricao {
    ABANDONO_ESCOLAR("Abandono Escolar"),
    DESCUMPRIMENTO_REGULAMENTO("Descumprimento de Regulamento"),
    DESEMPENHO_INSUFICIENTE("Desempenho Insuficiente"),
    DESISTENCIA_ENTIDADE("Desistência da Entidade"),
    ENCERRAMENTO_EMPRESA("Encerramento da Empresa"),
    FALECIMENTO("Falecimento"),
    FALTAS_SEM_JUSTIFICATIVAS("Faltas Sem Justificativas"),
    INGRESSO_OUTRA_ENTIDADE("Ingresso em Outra Entidade"),
    MUDANCA_MUNICIPIO("Mudança de Município"),
    OBTENCAO_EMPREGO_OU_ESTAGIO("Obtenção de Emprego ou Estágio"),
    PERDA_ANO_LETIVO("Perda do Ano Letivo"),
    REDUCAO_CUSTOS("Redução de Custos"),
    SOLICITACAO_EMPRESA("Solicitação da Empresa"),
    SOLICITACAO_ENTIDADE("Solicitação da Entidade"),
    SOLICITACAO_APRENDIZ("Solicitação do Aprendiz"),
    SOLICITACAO_RESPONSAVEL("Solicitação do Responsável"),
    TERMINO_CONTRATO("Término de Contrato");

    private final String descricao;

    DesligamentoMatricula(String descricao) {
        this.descricao = descricao;
    }

    @Override
    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static DesligamentoMatricula fromDescricao(String descricao) {
        return EnumUtils.fromDescricao(DesligamentoMatricula.class, descricao);
    }
}
