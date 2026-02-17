package com.projeto.cefom.novos.mapper;

import com.projeto.cefom.novos.dto.response.AulaListarResponseDTO;
import com.projeto.cefom.novos.dto.response.AulaResponseDTO;
import com.projeto.cefom.novos.dto.response.ChamadaResponseDTO;
import com.projeto.cefom.novos.dto.response.PresencaResponseDTO;
import com.projeto.cefom.novos.model.Aula;
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
}
