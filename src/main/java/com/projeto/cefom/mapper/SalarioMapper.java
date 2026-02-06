package com.projeto.cefom.mapper;

import com.projeto.cefom.dto.response.SalarioListarResponseDTO;
import com.projeto.cefom.dto.response.SalarioResponseDTO;
import com.projeto.cefom.model.Contrato;
import com.projeto.cefom.model.Salario;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Comparator;

@Component
public class SalarioMapper {

    public SalarioResponseDTO toResponseDTO (Contrato contrato, LocalDate data) {
        Salario salario = contrato.getSalarios()
                .stream()
                .filter(e -> e.estaValidoEm(data))
                .max(Comparator.comparing(Salario::getDataInicio))
                .orElse(null);

        return toResponseDTO(salario);
    }

    public SalarioResponseDTO toResponseDTO(Salario salario) {
        if (salario == null) return null;

        return new SalarioResponseDTO(
                salario.getIdSalario(),
                salario.getValorBase(),
                salario.getTipoSalario(),
                salario.getDataInicio(),
                salario.getDataFim()
        );
    }

    public SalarioListarResponseDTO toListarResponseDTO(Salario salario) {
        if (salario == null) return null;

        return new SalarioListarResponseDTO(
                salario.getIdSalario(),
                salario.getDataInicio(),
                salario.getDataFim(),
                salario.getValorBase()
        );
    }
}
