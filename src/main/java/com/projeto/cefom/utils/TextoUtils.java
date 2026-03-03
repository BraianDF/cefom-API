package com.projeto.cefom.utils;

import java.text.Normalizer;

public final class TextoUtils {

    private TextoUtils() {}

    public static boolean equalsFlex(String a, String b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;

        return normalizarSemAcento(a).equals(normalizarSemAcento(b));
    }

    public static boolean containsFlex(String texto, String termo) {
        if ((texto == null || texto.isBlank()) || (termo == null || termo.isBlank())) return false;

        String textoNorm = normalizarSemAcento(texto);
        String termoNorm = normalizarSemAcento(termo);

        return textoNorm.contains(termoNorm);
    }

    public static String normalizar(String string) {
        if (string == null || string.isBlank()) return null;
        return string.trim().replaceAll("\\s+", " ").toUpperCase();
    }

    public static String normalizarSemAcento(String string) {
        if (string == null || string.isBlank()) return null;

        String texto = string.trim().replaceAll("\\s+", " ");

        // separa letras de acentos
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD);

        // remove os acentos
        texto = texto.replaceAll("\\p{M}", "");

        // padroniza maiúsculo
        return texto.toUpperCase();
    }

    public static String capitalizar(String string) {
        if (string == null || string.isBlank()) return null;

        String[] palavras = string.trim().toLowerCase().split("\\s+");
        StringBuilder resultado = new StringBuilder();

        for (String p : palavras) {
            resultado.append(Character.toUpperCase(p.charAt(0)))
                    .append(p.substring(1))
                    .append(" ");
        }
        return resultado.toString().trim();
    }

    public static String textualizar(String string) {
        if (string == null || string.isBlank()) return null;
        //Implementar método
        return string;
    }

    public static String manterSomenteNumeros(String string) {
        if (string == null || string.isBlank()) return null;

        String numeros = string.replaceAll("\\D", "");
        return numeros.isBlank() ? null : numeros;
    }

    public static String formatarCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) return null;

        String numeros = manterSomenteNumeros(cpf);
        if (numeros.length() != 11) return numeros;

        return numeros.replaceFirst(
                "(\\d{3})(\\d{3})(\\d{3})(\\d{2})",
                "$1.$2.$3-$4"
        );
    }

    public static String formatarCnpj(String cnpj) {
        if (cnpj == null || cnpj.isBlank()) return null;

        String numeros = manterSomenteNumeros(cnpj);
        if (numeros.length() != 14) return numeros;

        return numeros.replaceFirst(
                "(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})",
                "$1.$2.$3/$4-$5"
        );
    }

    public static String formatarCep(String cep) {
        if (cep == null || cep.isBlank()) return null;

        String numeros = manterSomenteNumeros(cep);
        if (numeros.length() != 8) return numeros;

        return numeros.replaceFirst(
                "(\\d{5})(\\d{3})",
                "$1-$2"
        );
    }

    public static String formatarTelefone(String telefone) {
        if (telefone == null || telefone.isBlank()) return null;

        String numeros = manterSomenteNumeros(telefone);

        // 11 dígitos
        if (numeros.length() == 11) {
            return numeros.replaceFirst(
                    "(\\d{2})(\\d{5})(\\d{4})",
                    "($1) $2-$3"
            );
        }

        // 10 dígitos
        if (numeros.length() == 10) {
            return numeros.replaceFirst(
                    "(\\d{2})(\\d{4})(\\d{4})",
                    "($1) $2-$3"
            );
        }

        // se não for válido, retorna como veio
        return numeros;
    }
}
