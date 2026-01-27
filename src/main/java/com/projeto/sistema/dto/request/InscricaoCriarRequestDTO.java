package com.projeto.sistema.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record InscricaoCriarRequestDTO(
        @Valid
        @NotNull(message = "Inscrição é obrigatório.")
        InscricaoRequestDTO inscricao,
        @Valid
        @NotNull(message = "Documento é obrigatório.")
        DocumentoCpfRequestDTO documento,
        @Valid
        @NotNull(message = "Adolescente é obrigatório.")
        AdolescenteInscricaoRequestDTO adolescente,
        @Valid
        @NotNull(message = "Endereço é obrigatório.")
        EnderecoRequestDTO endereco,
        @Valid
        @NotNull(message = "Escolaridade é obrigatório.")
        EscolaridadeRequestDTO escolaridade,
        @Valid
        @NotNull(message = "Telefones são obrigatórios.")
        TelefonesRequestDTO telefones
) {
}
