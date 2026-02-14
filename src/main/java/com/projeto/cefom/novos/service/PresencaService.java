package com.projeto.cefom.novos.service;

import com.projeto.cefom.exceptions.RecursoNaoEncontradoException;
import com.projeto.cefom.exceptions.RegraNegocioException;
import com.projeto.cefom.model.Matricula;
import com.projeto.cefom.novos.dto.request.PresencaAvaliacaoRequestDTO;
import com.projeto.cefom.novos.dto.request.PresencaRequestDTO;
import com.projeto.cefom.novos.model.Aula;
import com.projeto.cefom.novos.model.Presenca;
import com.projeto.cefom.novos.repository.PresencaRepository;
import com.projeto.cefom.service.MatriculaService;
import org.springframework.stereotype.Service;

@Service
public class PresencaService {

    private final PresencaRepository presencaRepository;
    private final MatriculaService matriculaService;

    public PresencaService(PresencaRepository presencaRepository, MatriculaService matriculaService) {
        this.presencaRepository = presencaRepository;
        this.matriculaService = matriculaService;
    }

    public Presenca salvar(Presenca presenca) {
        return presencaRepository.save(presenca);
    }

    public Presenca buscarPresenca(Aula aula, Integer idMatricula) {
        Matricula aluno = matriculaService.buscarMatricula(idMatricula);

        Presenca presenca = presencaRepository.findByAulaIdAulaAndAlunoIdMatricula(aula.getIdAula(),idMatricula)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Presença com não encontrada."));

        return presenca;
    }

    public Presenca criarPresenca(PresencaRequestDTO dto, Aula aula) {
        Matricula aluno = matriculaService.buscarMatricula(dto.idMatricula());

        if (presencaRepository.existsByAulaIdAulaAndAlunoIdMatricula(aula.getIdAula(),dto.idMatricula())) {
            throw new RegraNegocioException("Presença do aluno com ID: "+dto.idMatricula()+" na aula com ID: "+aula.getIdAula()+" ja existe.");
        }

        Presenca presenca = new Presenca();
        presenca.setPresente(dto.presente());

        if (presenca.getIdPresenca() == null) {
            presenca = salvar(presenca);
        }

        aluno.adicionarPresenca(presenca);
        aula.adicionarPresenca(presenca);

        return salvar(presenca);
    }

    public Presenca atualizarPresenca(PresencaRequestDTO dto, Aula aula) {
        Presenca presenca = buscarPresenca(aula, dto.idMatricula());
        presenca.setPresente(dto.presente());
        return salvar(presenca);
    }

    public Presenca atualizarAvaliacao(PresencaAvaliacaoRequestDTO dto, Aula aula) {
        Presenca presenca = buscarPresenca(aula, dto.idMatricula());
        presenca.setAvaliacao(dto.avaliacao());
        presenca.setObservacao(dto.observacao());
        return salvar(presenca);
    }

    public void excluirPresenca(Aula aula, Integer idMatricula) {
        Presenca presenca = buscarPresenca(aula,idMatricula);
        Matricula aluno = matriculaService.buscarMatricula(idMatricula);

        aula.removerPresenca(presenca);
        aluno.removerPresenca(presenca);

        presencaRepository.deleteById(presenca.getIdPresenca());
    }
}
