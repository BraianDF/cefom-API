package com.projeto.sistema.image;

public record UploadFotoAdolescenteResponseDTO(
        Integer IdFotoAdolescente,
        String nomeArquivo,
        String tipoArquivo,
        long tamanho,
        String fileDownloadUri
) {
}
