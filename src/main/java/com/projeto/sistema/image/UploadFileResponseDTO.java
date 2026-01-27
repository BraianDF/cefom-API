package com.projeto.sistema.image;

public record UploadFileResponseDTO(
        String nomeArquivo,
        String tipoArquivo,
        long tamanho,
        String fileDownloadUri
) {
}
