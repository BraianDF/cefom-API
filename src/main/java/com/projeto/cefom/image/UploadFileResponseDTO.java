package com.projeto.cefom.image;

public record UploadFileResponseDTO(
        String nomeArquivo,
        String tipoArquivo,
        long tamanho,
        String fileDownloadUri
) {
}
