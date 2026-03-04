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
        if (adolescente == null) return null;

        Email email = adolescente.getEmails()
                .stream()
                .filter(e -> e.estaValidoEm(data))
                .max(Comparator.comparing(Email::getDataInicio))
                .orElse(null);

        return toResponseDTO(email);
    }

    public EmailResponseDTO toResponseDTO(Empresa empresa, LocalDate data) {
        if (empresa == null) return null;

        Email email = empresa.getEmails()
                .stream()
                .filter(e -> e.estaValidoEm(data))
                .max(Comparator.comparing(Email::getDataInicio))
                .orElse(null);

        return toResponseDTO(email);
    }

    public EmailResponseDTO toResponseDTO(Email email) {
        if (email == null) return null;

        return new EmailResponseDTO(
                email.getIdEmail(),
                email.getEmail(),
                email.getTitular(),
                email.getDataInicio(),
                email.getDataFim()
        );
    }

    public EmailListarResponseDTO toListarResponseDTO(Email email) {
        if (email == null) return null;

        return new EmailListarResponseDTO(
                email.getIdEmail(),
                email.getDataInicio(),
                email.getDataFim(),
                email.getEmail()
        );
    }
}
