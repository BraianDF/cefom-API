package com.projeto.cefom.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.projeto.cefom.utils.EnumComDescricao;
import com.projeto.cefom.utils.EnumUtils;

public enum Parentesco implements EnumComDescricao {
    MAE ("Mãe"),
    PAI ("Pai"),
    IRMA ("Irmã"),
    IRMAO ("Irmão"),
    MADRASTA ("Madrasta"),
    PADRASTO ("Padrasto"),
    AVO_FEMININO ("Avó"),
    AVO_MASCULINO ("Avô"),
    TIA ("Tia"),
    TIO ("Tio"),
    PRIMA ("Prima"),
    PRIMO ("Primo"),
    FILHA ("Filha"),
    FILHO ("Filho"),
    ENTIADA ("Entiada"),
    ENTIADO ("Entiado"),
    SOBRINHA ("Sobrinha"),
    SOBRINHO ("Sobrinho"),
    CUNHADA ("Cunhada"),
    CUNHADO ("Cunhado"),
    SOGRA ("Sogra"),
    SOGRO ("Sogro"),
    NAMORADA ("Namorada"),
    NAMORADO ("Namorado"),
    ESPOSA ("Esposa"),
    MARIDO ("Marido"),
    BISAVO_FEMININO ("Bisavó"),
    BISAVO_MASCULINO ("Bisavô"),
    NETA ("Neta"),
    NETO ("Neto"),
    NORA ("Nora"),
    GENRO ("Genro"),
    RESPONSAVEL ("Responsável");
    private final String descricao;

    Parentesco(String descricao) {
        this.descricao = descricao;
    }

    @Override
    @JsonValue
    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static Parentesco fromDescricao(String descricao) {
        return EnumUtils.fromDescricao(Parentesco.class, descricao);
    }
}
