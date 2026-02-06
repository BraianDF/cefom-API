package com.projeto.cefom.mapper;

import com.projeto.cefom.dto.response.EmailListarResponseDTO;
import com.projeto.cefom.dto.response.EmailResponseDTO;
import com.projeto.cefom.model.Adolescente;
import com.projeto.cefom.model.Email;
import com.projeto.cefom.model.Empresa;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.Comparator;

@Component
public class EmailMapper {

    public EmailResponseDTO toResponseDTO(Adolescente adolescente, LocalDate data) {
        Email email = adolescente.getEmails()
                .stream()
                .filter(e -> e.estaValidoEm(data))
                .max(Comparator.comparing(Email::getDataInicio))
                .orElse(null);

        return toResponseDTO(email);
    }

    public EmailResponseDTO toResponseDTO(Empresa empresa, LocalDate data) {
        Email email = empresa.getEmails()
                .stream()
                .filter(e -> e.estaValidoEm(data))
                .max(Comparator.comparing(Email::getDataInicio))
                .orElse(null);

        return toResponseDTO(email);
    }

    public EmailResponseDTO toResponseDTO(Email email) {
        return new EmailResponseDTO(
                email != null ? email.getIdEmail() : null,
                email != null ? email.getEmail() : null,
                email != null ? email.getTitular() : null,
                email != null ? email.getDataInicio() : null,
                email != null ? email.getDataFim() : null
        );
    }

    public EmailListarResponseDTO toListarResponseDTO(Email email) {
        return new EmailListarResponseDTO(
                email != null ? email.getIdEmail() : null,
                email != null ? email.getDataInicio() : null,
                email != null ? email.getDataFim() : null,
                email != null ? email.getEmail() : null
        );
    }
}
