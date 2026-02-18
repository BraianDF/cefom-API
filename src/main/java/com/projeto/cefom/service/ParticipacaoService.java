package com.projeto.cefom.service;

import com.projeto.cefom.exceptions.RegraNegocioException;
import com.projeto.cefom.model.Matricula;
import com.projeto.cefom.model.Participacao;
import com.projeto.cefom.model.Turma;
import com.projeto.cefom.repository.ParticipacaoRepository;
import com.projeto.cefom.service.MatriculaService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ParticipacaoService {

    private final ParticipacaoRepository participacaoRepository;
    private final MatriculaService matriculaService;

    public ParticipacaoService(ParticipacaoRepository participacaoRepository, MatriculaService matriculaService) {
        this.participacaoRepository = participacaoRepository;
        this.matriculaService = matriculaService;
    }

    public void adicionarTurmaAluno(Turma turma, Integer idMatricula, LocalDate data) {
        Matricula aluno = matriculaService.buscarMatricula(idMatricula);

        List<Participacao> participacaosExistentes = participacaoRepository
                .findByTurmaIdTurmaAndAlunoIdMatricula(turma.getIdTurma(),aluno.getIdMatricula());

        boolean jaExisteValida = participacaosExistentes.stream().anyMatch(p -> p.estaValidoEm(data));

        if (jaExisteValida) {
            throw new RegraNegocioException("O aluno já possui uma participação válida nessa turma na data informada.");
        }

        Participacao participacao = new Participacao();
        participacao.setDataInicio(data);

        if (participacao.getIdParticipacao() == null) {
            participacao = participacaoRepository.save(participacao);
        }

        turma.adicionarAluno(participacao);
        aluno.adicionarTurma(participacao);
    }

    public void removerTurmaAluno(Turma turma, Integer idMatricula, LocalDate data) {
        Matricula aluno = matriculaService.buscarMatricula(idMatricula);

        Participacao participacao = participacaoRepository.findByTurmaIdTurmaAndAlunoIdMatriculaAndDataFimIsNull(turma.getIdTurma(),idMatricula)
                .orElseThrow(() -> new RegraNegocioException("Não existe participação ativa do aluno com Id "+idMatricula+" na turma com Id "+turma.getIdTurma()+"."));

        participacao.setDataFim(data);
        participacaoRepository.save(participacao);
    }

}
