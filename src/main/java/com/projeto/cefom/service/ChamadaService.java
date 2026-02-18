package com.projeto.cefom.service;

import com.projeto.cefom.exceptions.RecursoNaoEncontradoException;
import com.projeto.cefom.exceptions.RegraNegocioException;
import com.projeto.cefom.model.Matricula;
import com.projeto.cefom.dto.request.ChamadaAvaliacaoRequestDTO;
import com.projeto.cefom.dto.request.ChamadaRequestDTO;
import com.projeto.cefom.dto.response.ChamadaAvaliacaoResponseDTO;
import com.projeto.cefom.dto.response.ChamadaResponseDTO;
import com.projeto.cefom.mapper.AulaMapper;
import com.projeto.cefom.model.Aula;
import com.projeto.cefom.model.Presenca;
import com.projeto.cefom.repository.AulaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ChamadaService {
    private final AulaRepository aulaRepository;
    private final AulaMapper aulaMapper;
    private final PresencaService presencaService;

    public ChamadaService(AulaRepository aulaRepository, AulaMapper aulaMapper, PresencaService presencaService) {
        this.aulaRepository = aulaRepository;
        this.aulaMapper = aulaMapper;
        this.presencaService = presencaService;
    }

    @Transactional
    public ChamadaResponseDTO criar(Integer idAula, ChamadaRequestDTO dto) {
        Aula aula = buscarAula(idAula);
        if (aula.getChamadaRealizada()) {
            throw new RegraNegocioException("A Chamada para essa aula ja foi realizada.");
        }

        Map<Integer, Boolean> mapaPresencas = dto.alunos().stream()
                .collect(Collectors.toMap(
                        aluno -> aluno.idMatricula(),
                        aluno -> aluno.presente()
                ));

        Set<Integer> idsAlunosAula = aula.getAlunos().stream()
                .map(Matricula::getIdMatricula)
                .collect(Collectors.toSet());

        mapaPresencas.keySet().forEach(id -> {
            if (!idsAlunosAula.contains(id)) {
                throw new RegraNegocioException("Aluno com ID " + id + " não pertence à aula com ID " + aula.getIdAula()+".");
            }
        });

        idsAlunosAula.forEach(matricula -> {

            Boolean presente = mapaPresencas.get(matricula);

            if (presente == null) {
                presente = false;
            }

            presencaService.criarPresenca(matricula, presente, aula);
        });

        return aulaMapper.toChamadaResponseDTO(aula);
    }

    @Transactional
    public ChamadaResponseDTO atualizar(Integer idAula, ChamadaRequestDTO dto) {
        Aula aula = buscarAula(idAula);
        if (!aula.getChamadaRealizada()) {
            throw new RegraNegocioException("A Chamada para essa aula ainda não foi realizada.");
        }

        Map<Integer, Boolean> mapaPresencas = dto.alunos().stream()
                .collect(Collectors.toMap(
                        aluno -> aluno.idMatricula(),
                        aluno -> aluno.presente()
                ));

        Set<Integer> idsAlunosAula = aula.getPresencas().stream()
                .map(p -> p.getAluno().getIdMatricula())
                .collect(Collectors.toSet());

        mapaPresencas.keySet().forEach(id -> {
            if (!idsAlunosAula.contains(id)) {
                throw new RegraNegocioException("Aluno com ID " + id + " não pertence à aula com ID " + aula.getIdAula()+".");
            }
        });

        idsAlunosAula.forEach(matricula -> {

            Boolean presente = mapaPresencas.get(matricula);

            if (presente == null) {
                presente = false;
            }

            presencaService.atualizarPresenca(matricula, presente, aula);
        });

        return aulaMapper.toChamadaResponseDTO(aula);
    }

    @Transactional(readOnly = true)
    public ChamadaResponseDTO listar(Integer idAula) {
        Aula aula = buscarAula(idAula);
        return aulaMapper.toChamadaListarResponseDTO(aula);
    }

    @Transactional
    public void excluirPorId(Integer idAula) {
        Aula aula = buscarAula(idAula);
        if (!aula.getChamadaRealizada()) {
            throw new RegraNegocioException("A Chamada para essa aula ainda não foi realizada.");
        }

        Set<Matricula> idsAlunos = aula.getPresencas().stream().map(Presenca::getAluno).collect(Collectors.toSet());

        idsAlunos.forEach(aluno -> presencaService.excluirPresenca(aula,aluno.getIdMatricula()));
    }

    @Transactional
    public ChamadaAvaliacaoResponseDTO atualizarAvaliacao(Integer idAula, ChamadaAvaliacaoRequestDTO dto) {
        Aula aula = buscarAula(idAula);
        if (!aula.getChamadaRealizada()) {
            throw new RegraNegocioException("A Chamada para essa aula ainda não foi realizada.");
        }

        Set<Integer> idsAlunos = aula.getPresencas().stream().map(p -> p.getAluno().getIdMatricula()).collect(Collectors.toSet());

        dto.alunos().forEach(aluno -> {
            if (!idsAlunos.contains(aluno.idMatricula())) {
                throw new RegraNegocioException("Aluno com ID " + aluno.idMatricula() + " não pertence à aula com ID "+aula.getIdAula()+".");
            }
            presencaService.atualizarAvaliacao(aluno,aula);
        });

        return aulaMapper.toAvaliacaoListarResponseDTO(aula);
    }

    @Transactional(readOnly = true)
    public ChamadaAvaliacaoResponseDTO listarAvaliacao(Integer idAula) {
        Aula aula = buscarAula(idAula);
        return aulaMapper.toAvaliacaoListarResponseDTO(aula);
    }

    public Aula salvar(Aula aula) {
        return aulaRepository.save(aula);
    }

    public Aula buscarAula(Integer idAula) {
        Aula aula = aulaRepository.findById(idAula)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aula com ID "+idAula+" não encontrada."));
        return aula;
    }

}
