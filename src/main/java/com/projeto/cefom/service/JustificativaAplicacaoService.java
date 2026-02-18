package com.projeto.cefom.service;

import com.projeto.cefom.model.FaltaTrabalho;
import com.projeto.cefom.model.JustificativaFalta;
import com.projeto.cefom.model.Presenca;
import com.projeto.cefom.repository.FaltaTrabalhoRepository;
import com.projeto.cefom.repository.JustificativaFaltaRepository;
import com.projeto.cefom.repository.PresencaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class JustificativaAplicacaoService {

    private final JustificativaFaltaRepository justificativaFaltaRepository;
    private final PresencaRepository presencaRepository;
    private final FaltaTrabalhoRepository faltaTrabalhoRepository;

    public JustificativaAplicacaoService(JustificativaFaltaRepository justificativaFaltaRepository, PresencaRepository presencaRepository, FaltaTrabalhoRepository faltaTrabalhoRepository) {
        this.justificativaFaltaRepository = justificativaFaltaRepository;
        this.presencaRepository = presencaRepository;
        this.faltaTrabalhoRepository = faltaTrabalhoRepository;
    }

    public void aplicarJustificativa(Presenca presenca) {

        List<JustificativaFalta> justificativas = justificativaFaltaRepository
                .findByAlunoIdMatriculaAndDataInicioLessThanEqualAndDataFimGreaterThanEqual(
                        presenca.getAluno().getIdMatricula(),
                        presenca.getAula().getDataInicio(),
                        presenca.getAula().getDataFim()
                );

        justificativas.stream()
                .max(Comparator.comparing(j -> j.getMotivo().getPrioridade()))
                .ifPresent(j -> j.adicionarPresenca(presenca));
    }

    public void aplicarJustificativa(FaltaTrabalho faltaTrabalho) {

        List<JustificativaFalta> justificativas = justificativaFaltaRepository
                .findByAlunoIdMatriculaAndDataInicioLessThanEqualAndDataFimGreaterThanEqual(
                        faltaTrabalho.getContrato().getMatricula().getIdMatricula(),
                        faltaTrabalho.getDataInicio(),
                        faltaTrabalho.getDataFim()
                );

        justificativas.stream()
                .max(Comparator.comparing(j -> j.getMotivo().getPrioridade()))
                .ifPresent(j -> j.adicionarFaltaTrabalho(faltaTrabalho));
    }

    public void aplicarJustificativa(JustificativaFalta justificativaFalta) {

        List<Presenca> presencas = presencaRepository
                .findByAlunoIdMatriculaAndAulaDataInicioBetween(
                        justificativaFalta.getAluno().getIdMatricula(),
                        justificativaFalta.getDataInicio(),
                        justificativaFalta.getDataFim()
                );

        presencas.forEach(p -> {
            JustificativaFalta justificativaAtual = p.getJustificativa();

            if (justificativaAtual == null) {
                justificativaFalta.adicionarPresenca(p);
                return;
            }

            if (justificativaFalta.getMotivo().getPrioridade() > justificativaAtual.getMotivo().getPrioridade()) {
                justificativaAtual.removerPresenca(p);
                justificativaFalta.adicionarPresenca(p);
            }
        });

        List<FaltaTrabalho> faltasTrabalhos = faltaTrabalhoRepository
                .findByContratoMatriculaIdMatriculaAndAndDataInicioBetween(
                        justificativaFalta.getAluno().getIdMatricula(),
                        justificativaFalta.getDataInicio(),
                        justificativaFalta.getDataFim()
                );

        faltasTrabalhos.forEach(f -> {
            JustificativaFalta justificativaAtual = f.getJustificativa();

            if (justificativaAtual == null) {
                justificativaFalta.adicionarFaltaTrabalho(f);
                return;
            }

            if (justificativaFalta.getMotivo().getPrioridade() > justificativaAtual.getMotivo().getPrioridade()) {
                justificativaAtual.removerFaltaTrabalho(f);
                justificativaFalta.adicionarFaltaTrabalho(f);
            }
        });
    }

    public void reAplicarJustificativa(Presenca presenca) {
        if (presenca.getJustificativa() != null) {
            presenca.getJustificativa().removerPresenca(presenca);
        }
        aplicarJustificativa(presenca);
    }

    public void reAplicarJustificativa(FaltaTrabalho falta) {
        if (falta.getJustificativa() != null) {
            falta.getJustificativa().removerFaltaTrabalho(falta);
        }
        aplicarJustificativa(falta);
    }

    public void reAplicarJustificativa(JustificativaFalta justificativa) {
        List<Presenca> presencas = new ArrayList<>(justificativa.getPresencas());
        List<FaltaTrabalho> faltaTrabalhos = new ArrayList<>(justificativa.getFaltasTrabalhos());

        presencas.forEach(presenca -> reAplicarJustificativa(presenca));

        faltaTrabalhos.forEach(falta -> reAplicarJustificativa(falta));

        aplicarJustificativa(justificativa);
    }
}
