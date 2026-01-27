package com.projeto.sistema.utils;

public class EnumUtils {

    public static <T extends Enum<T> & EnumComDescricao> T fromDescricao(Class<T> enumClass, String descricao) {
        for (T constante : enumClass.getEnumConstants()) {
            if (constante.getDescricao().equalsIgnoreCase(descricao)) {
                return constante;
            }
        }
        throw new IllegalArgumentException("Valor inválido: " + descricao);
    }

}
