package com.projeto.cefom.novos.service;

import com.projeto.cefom.exceptions.RecursoNaoEncontradoException;
import com.projeto.cefom.novos.dto.request.ProfessorRequestDTO;
import com.projeto.cefom.novos.model.Disciplina;
import com.projeto.cefom.novos.model.Lecionamento;
import com.projeto.cefom.novos.model.Professor;
import com.projeto.cefom.novos.repository.LecionamentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LecionamentoService {

    private final LecionamentoRepository lecionamentoRepository;
    private final ProfessorService professorService;
    private final DisciplinaService disciplinaService;

    public LecionamentoService(LecionamentoRepository lecionamentoRepository, ProfessorService professorService, DisciplinaService disciplinaService) {
        this.lecionamentoRepository = lecionamentoRepository;
        this.professorService = professorService;
        this.disciplinaService = disciplinaService;
    }

    public Lecionamento salvar(Lecionamento lecionamento) {
        return lecionamentoRepository.save(lecionamento);
    }

    @Transactional
    public Lecionamento buscarLecionamento(Integer idProfessor, Integer idDisciplina) {
        Professor professor = professorService.buscarProfessor(idProfessor);
        Disciplina disciplina = disciplinaService.buscarDisciplina(idDisciplina);

        Lecionamento lecionamento = lecionamentoRepository.findLecionamentoByProfessorIdProfessorAndDisciplinaIdDisciplina(idProfessor,idDisciplina);

        if (lecionamento == null) {
            lecionamento = criarLecionamento(professor,disciplina);
        }

        return lecionamento;
    }

    private Lecionamento criarLecionamento(Professor professor, Disciplina disciplina) {
        Lecionamento lecionamento = new Lecionamento();

        if (lecionamento.getIdLecionamento() == null) {
            lecionamento = salvar(lecionamento);
        }

        professor.adicionarDisciplina(lecionamento);
        disciplina.adicionarProfessor(lecionamento);

        return salvar(lecionamento);
    }

    @Transactional
    public void deletarLecionamento(Lecionamento lecionamento) {

        lecionamento.getProfessor().removerDisciplina(lecionamento);
        lecionamento.getDisciplina().removerProfessor(lecionamento);

        lecionamentoRepository.deleteById(lecionamento.getIdLecionamento());
    }
}
