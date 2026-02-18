package com.projeto.cefom.mapper;

import com.projeto.cefom.dto.response.AlunoParticipacaoResponseDTO;
import com.projeto.cefom.model.Participacao;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ParticipacaoMapper {

    private final MatriculaMapper matriculaMapper;

    public ParticipacaoMapper(MatriculaMapper matriculaMapper) {
        this.matriculaMapper = matriculaMapper;
    }

    public AlunoParticipacaoResponseDTO toResponseDTO(Participacao participacao, LocalDate data) {
        if (participacao == null) return null;
        return new AlunoParticipacaoResponseDTO(
                participacao.getIdParticipacao(),
                participacao.getDataInicio(),
                participacao.getDataFim(),
                matriculaMapper.toAlunoResponse(participacao.getAluno(), data)
        );
    }
}
