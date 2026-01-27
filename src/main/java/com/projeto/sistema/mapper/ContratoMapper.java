package com.projeto.sistema.mapper;

import com.projeto.sistema.dto.response.ContratoListarResponseDTO;
import com.projeto.sistema.dto.response.ContratoResponseDTO;
import com.projeto.sistema.model.Contrato;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ContratoMapper {
    private final EmpresaMapper empresaMapper;
    private final CargoMapper cargoMapper;
    private final SalarioMapper salarioMapper;
    private final JornadaTrabalhoMapper jornadaTrabalhoMapper;

    public ContratoMapper(EmpresaMapper empresaMapper, CargoMapper cargoMapper, SalarioMapper salarioMapper, JornadaTrabalhoMapper jornadaTrabalhoMapper) {
        this.empresaMapper = empresaMapper;
        this.cargoMapper = cargoMapper;
        this.salarioMapper = salarioMapper;
        this.jornadaTrabalhoMapper = jornadaTrabalhoMapper;
    }

    public ContratoResponseDTO toResponseDTO (Contrato contrato, LocalDate data) {
        if (contrato == null) return null;

        return new ContratoResponseDTO(
                contrato.getIdContrato(),
                contrato.getSituacaoContratoEm(data),
                contrato.getDataInicio(),
                contrato.getDataTermino(),
                contrato.getDataFim(),
                contrato.getDesligamento(),
                contrato.getEfetivado(),
                contrato.getTipoContratacao(),
                empresaMapper.toResponseDTO(contrato.getEmpresa()),
                cargoMapper.toResponseDTO(contrato, data),
                salarioMapper.toResponseDTO(contrato, data),
                jornadaTrabalhoMapper.toResponseDTO(contrato, data)
        );
    }


    public ContratoListarResponseDTO toListarResponseDTO (Contrato contrato) {
        if (contrato == null) return null;

        return new ContratoListarResponseDTO(
                contrato.getIdContrato(),
                contrato.getDataInicio(),
                contrato.getDataFim(),
                empresaMapper.toListarResponseDTO(contrato.getEmpresa())
        );
    }
}
