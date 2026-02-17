package com.projeto.cefom.novos.service;

import com.projeto.cefom.exceptions.RecursoNaoEncontradoException;
import com.projeto.cefom.exceptions.RegraNegocioException;
import com.projeto.cefom.model.Matricula;
import com.projeto.cefom.novos.dto.request.PresencaAvaliacaoRequestDTO;
import com.projeto.cefom.novos.model.Aula;
import com.projeto.cefom.novos.model.Presenca;
import com.projeto.cefom.novos.repository.PresencaRepository;
import com.projeto.cefom.service.MatriculaService;
import org.springframework.stereotype.Service;

@Service
public class PresencaService {

    private final PresencaRepository presencaRepository;
    private final MatriculaService matriculaService;
    private final JustificativaAplicacaoService justificativaAplicacaoService;

    public PresencaService(PresencaRepository presencaRepository, MatriculaService matriculaService, JustificativaAplicacaoService justificativaAplicacaoService) {
        this.presencaRepository = presencaRepository;
        this.matriculaService = matriculaService;
        this.justificativaAplicacaoService = justificativaAplicacaoService;
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

    public Presenca criarPresenca(Integer idMatricula, boolean presente, Aula aula) {
        Matricula aluno = matriculaService.buscarMatricula(idMatricula);

        if (presencaRepository.existsByAulaIdAulaAndAlunoIdMatricula(aula.getIdAula(),idMatricula)) {
            throw new RegraNegocioException("Presença do aluno com ID: "+idMatricula+" na aula com ID: "+aula.getIdAula()+" ja existe.");
        }

        Presenca presenca = new Presenca();
        presenca.setPresente(presente);

        if (presenca.getIdPresenca() == null) {
            presenca = salvar(presenca);
        }

        aluno.adicionarPresenca(presenca);
        aula.adicionarPresenca(presenca);

        justificativaAplicacaoService.aplicarJustificativa(presenca);

        return salvar(presenca);
    }

    public Presenca atualizarPresenca(Integer idMatricula, boolean presente, Aula aula) {
        Presenca presenca = buscarPresenca(aula, idMatricula);
        presenca.setPresente(presente);
        if (!presente) presenca.setAvaliacao(0); //Caso falte, avaliação 0
        return salvar(presenca);
    }

    public Presenca atualizarAvaliacao(PresencaAvaliacaoRequestDTO dto, Aula aula) {
        Presenca presenca = buscarPresenca(aula, dto.idMatricula());

        if (dto.avaliacao().getCodigo() != 0 && !presenca.getPresente()) {
            throw new RegraNegocioException("Não é possível avaliar aluno ausente.");
        }

        presenca.setAvaliacao(dto.avaliacao().getCodigo());

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
