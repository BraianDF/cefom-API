package com.projeto.cefom.image.dto;

public record UploadFileResponseDTO(
        String nomeArquivo,
        String tipoArquivo,
        long tamanho,
        String fileDownloadUri
) {
}
