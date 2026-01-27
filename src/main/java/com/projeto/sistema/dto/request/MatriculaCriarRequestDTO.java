package com.projeto.sistema.dto.request;

import com.projeto.sistema.image.UploadFotoAdolescenteResponseDTO;
import jakarta.validation.constraints.NotNull;


public record MatriculaCriarRequestDTO(
        @NotNull(message = "Matrícula é obrigatório.")
        MatriculaRequestDTO matricula,
        @NotNull(message = "Adolescente é obrigatório.")
        AdolescenteMatriculaRequestDTO adolescente,
        @NotNull(message = "Endereço é obrigatório.")
        EnderecoMatriculaRequestDTO endereco,
        @NotNull(message = "Telefones são obrigatórios.")
        TelefonesRequestDTO telefones,
        String emailAdolescente,
        @NotNull(message = "Documento é obrigatório.")
        DocumentoAdolescenteRequestDTO documento,
        @NotNull(message = "Caracteristica é obrigatório.")
        CaracteristicaRequestDTO caracteristica,
        @NotNull(message = "Escolaridade é obrigatório.")
        EscolaridadeRequestDTO escolaridade,
        @NotNull(message = "Dados social são obrigatórios.")
        DadosSocialRequestDTO dadosSocial,
        @NotNull(message = "Familiares são obrigatórios.")
        FamiliaresRequestDTO familiares
) {
}
