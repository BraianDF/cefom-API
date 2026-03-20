package com.projeto.cefom.mapper;

import com.projeto.cefom.dto.response.*;
import com.projeto.cefom.enums.AvaliacaoAluno;
import com.projeto.cefom.model.Aula;
import com.projeto.cefom.utils.TextoUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AulaMapper {

    private final ProfessorMapper professorMapper;
    private final DisciplinaMapper disciplinaMapper;
    private final SalaAulaMapper salaAulaMapper;
    private final TurmaMapper turmaMapper;

    public AulaMapper(ProfessorMapper professorMapper, DisciplinaMapper disciplinaMapper, SalaAulaMapper salaAulaMapper, TurmaMapper turmaMapper) {
        this.professorMapper = professorMapper;
        this.disciplinaMapper = disciplinaMapper;
        this.salaAulaMapper = salaAulaMapper;
        this.turmaMapper = turmaMapper;
    }

    public AulaResponseDTO toResponseDTO(Aula aula) {
        if (aula == null) return null;
        return new AulaResponseDTO(
                aula.getIdAula(),
                aula.getChamadaRealizada(),
                aula.getDataInicio(),
                aula.getHorarioInicio(),
                aula.getHorarioFim(),
                professorMapper.toResponseDTO(aula.getLecionamento().getProfessor()),
                disciplinaMapper.toResponseDTO(aula.getLecionamento().getDisciplina()),
                salaAulaMapper.toResponseDTO(aula.getSala()),
                turmaMapper.toResponseDTO(aula.getTurma(),aula.getDataInicio())
        );
    }

    public AulaListarResponseDTO toListarResponseDTO(Aula aula) {
        if (aula == null) return null;
        return new AulaListarResponseDTO(
                aula.getIdAula(),
                aula.getChamadaRealizada(),
                aula.getDataInicio(),
                aula.getHorarioInicio(),
                aula.getHorarioFim(),
                aula.getLecionamento().getProfessor().getNome(),
                aula.getLecionamento().getDisciplina().getNome()
        );
    }

    public ChamadaResponseDTO toChamadaResponseDTO(Aula aula) {
        if (aula == null) return null;

        List<PresencaResponseDTO> alunos = aula.getPresencas()
                .stream()
                .map(a -> new PresencaResponseDTO(a.getAluno().getIdMatricula(), a.getPresente()))
                .toList();

        return new ChamadaResponseDTO(alunos);
    }

    public ChamadaResponseDTO toChamadaListarResponseDTO(Aula aula) {
        if (aula == null) return null;

        if (aula.getChamadaRealizada()) return toChamadaResponseDTO(aula);

        List<PresencaResponseDTO> alunos = aula.getAlunos()
                .stream()
                .map(a -> new PresencaResponseDTO(a.getIdMatricula(), null))
                .toList();

        return new ChamadaResponseDTO(alunos);
    }

    public ChamadaAvaliacaoResponseDTO toAvaliacaoResponseDTO(Aula aula) {
        if (aula == null) return null;

        List<PresencaAvaliacaoResponseDTO> alunos = aula.getPresencas()
                .stream()
                .map(a -> new PresencaAvaliacaoResponseDTO(a.getAluno().getIdMatricula(), AvaliacaoAluno.fromCodigo(a.getAvaliacao(), a.getPresente()), TextoUtils.capitalizarPrimeiraLetra(a.getObservacao())))
                .toList();

        return new ChamadaAvaliacaoResponseDTO(alunos);
    }

    public ChamadaAvaliacaoResponseDTO toAvaliacaoListarResponseDTO(Aula aula) {
        if (aula == null) return null;

        if (aula.getChamadaRealizada()) return toAvaliacaoResponseDTO(aula);

        List<PresencaAvaliacaoResponseDTO> alunos = aula.getAlunos()
                .stream()
                .map(a -> new PresencaAvaliacaoResponseDTO(a.getIdMatricula(), AvaliacaoAluno.fromCodigo(0,true), null))
                .toList();

        return new ChamadaAvaliacaoResponseDTO(alunos);
    }
}
