package com.projeto.cefom.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class FileNameUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    public static String gerarNomeFisico(String nomeOriginal) {
        // pega a extensão (se existir)
        String extensao = "";

        int index = nomeOriginal.lastIndexOf('.');
        if (index > 0) {
            extensao = nomeOriginal.substring(index);
        }

        String uuid = UUID.randomUUID().toString();
        String dataHora = LocalDateTime.now().format(FORMATTER);

        return dataHora + "_" + uuid + extensao;
    }
}