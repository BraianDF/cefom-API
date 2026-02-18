package com.projeto.cefom.image.dto;

public record UploadFotoAdolescenteResponseDTO(
        Integer IdFotoAdolescente,
        String nomeArquivo,
        String tipoArquivo,
        long tamanho,
        String fileDownloadUri
) {
}
