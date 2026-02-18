package com.projeto.cefom.service;

import com.projeto.cefom.exceptions.RecursoNaoEncontradoException;
import com.projeto.cefom.exceptions.RegraNegocioException;
import com.projeto.cefom.model.Matricula;
import com.projeto.cefom.dto.request.JustificativaFaltaRequestDTO;
import com.projeto.cefom.dto.response.JustificativaFaltaListarResponseDTO;
import com.projeto.cefom.dto.response.JustificativaFaltaResponseDTO;
import com.projeto.cefom.enums.MotivoJustificativa;
import com.projeto.cefom.mapper.JustificativaFaltaMapper;
import com.projeto.cefom.model.JustificativaFalta;
import com.projeto.cefom.repository.JustificativaFaltaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class JustificativaFaltaService {

    private final JustificativaFaltaRepository justificativaFaltaRepository;
    private final JustificativaFaltaMapper justificativaFaltaMapper;
    private final MatriculaService matriculaService;
    private final JustificativaAplicacaoService justificativaAplicacaoService;

    public JustificativaFaltaService(JustificativaFaltaRepository justificativaFaltaRepository, JustificativaFaltaMapper justificativaFaltaMapper, MatriculaService matriculaService, JustificativaAplicacaoService justificativaAplicacaoService) {
        this.justificativaFaltaRepository = justificativaFaltaRepository;
        this.justificativaFaltaMapper = justificativaFaltaMapper;
        this.matriculaService = matriculaService;
        this.justificativaAplicacaoService = justificativaAplicacaoService;
    }

    @Transactional
    public JustificativaFaltaResponseDTO criar(Integer idAdolescente, Integer idMatricula, JustificativaFaltaRequestDTO dto) {
        Matricula aluno = matriculaService.buscarMatriculaAdolescente(idAdolescente,idMatricula);

        if (justificativaFaltaRepository.existsByAlunoIdMatriculaAndMotivoAndDataInicioAndQtdeDias(idMatricula,dto.motivo(),dto.dataInicio(),dto.qtdeDias())) {
            throw new RegraNegocioException("Justificativa de Falta já cadastrada.");
        }

        JustificativaFalta justificativa = salvar(criarJustificativa(dto, aluno));
        return justificativaFaltaMapper.toResponseDTO(justificativa);
    }

    @Transactional
    public JustificativaFaltaResponseDTO atualizar(Integer idAdolescente, Integer idMatricula, Integer idJustificativaFalta, JustificativaFaltaRequestDTO dto) {
        Matricula aluno = matriculaService.buscarMatriculaAdolescente(idAdolescente,idMatricula);

        if (justificativaFaltaRepository.existsByAlunoIdMatriculaAndMotivoAndDataInicioAndQtdeDiasAndIdJustificativaFaltaNot(idMatricula,dto.motivo(),dto.dataInicio(),dto.qtdeDias(),idJustificativaFalta)) {
            throw new RegraNegocioException("Justificativa de Falta já cadastrada.");
        }

        JustificativaFalta justificativa = buscarJustificativaMatricula(idAdolescente,idMatricula,idJustificativaFalta);
        justificativa = salvar(atualizarJustificativa(dto, justificativa));
        return justificativaFaltaMapper.toResponseDTO(justificativa);
    }

    @Transactional(readOnly = true)
    public JustificativaFaltaResponseDTO buscarPorId(Integer idAdolescente, Integer idMatricula, Integer idJustificativaFalta) {
        JustificativaFalta justificativa = buscarJustificativaMatricula(idAdolescente, idMatricula, idJustificativaFalta);
        return justificativaFaltaMapper.toResponseDTO(justificativa);
    }

    @Transactional(readOnly = true)
    public Page<JustificativaFaltaListarResponseDTO> listar(Integer idAdolescente, Pageable pageable) {
        return justificativaFaltaRepository.findByAlunoAdolescenteIdAdolescente(idAdolescente, pageable)
                .map(justificativaFaltaMapper::toListarResponseDTO);
    }

    @Transactional
    public void excluirPorId(Integer idAdolescente, Integer idMatricula, Integer idJustificativaFalta) {
        JustificativaFalta justificativa = buscarJustificativaMatricula(idAdolescente, idMatricula, idJustificativaFalta);
        justificativaFaltaRepository.deleteById(idJustificativaFalta);
    }

    public JustificativaFalta salvar(JustificativaFalta justificativa) {
        return justificativaFaltaRepository.save(justificativa);
    }

    public JustificativaFalta buscarJustificativaMatricula(Integer idAdolescente, Integer idMatricula, Integer idJustificativaFalta) {
        Matricula matricula = matriculaService.buscarMatriculaAdolescente(idAdolescente, idMatricula);

        JustificativaFalta justificativa = buscarJustificativa(idJustificativaFalta);

        if (justificativa.getAluno() == null) {
            throw new RegraNegocioException("Justificativa de Falta não pertence a uma matrícula.");
        }

        if (!justificativa.getAluno().getIdMatricula().equals(idMatricula)) {
            throw new RegraNegocioException("Justificativa de Falta não pertence a matrícula.");
        }

        return justificativa;
    }

    public JustificativaFalta buscarJustificativa(Integer idJustificativaFalta) {
        JustificativaFalta justificativa = justificativaFaltaRepository.findById(idJustificativaFalta)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Justificativa de Falta com ID "+idJustificativaFalta+" não encontrada."));
        return justificativa;
    }

    public JustificativaFalta criarJustificativa(JustificativaFaltaRequestDTO dto, Matricula matricula) {
        JustificativaFalta justificativa = new JustificativaFalta();
        justificativa.setMotivo(dto.motivo());
        justificativa.setDataInicio(dto.dataInicio());
        justificativa.setQtdeDias(dto.qtdeDias());
        justificativa.setDataFim(dto.dataInicio(), dto.qtdeDias());
        justificativa.setObservacao(dto.observacao());

        if (justificativa.getIdJustificativaFalta() == null) {
            justificativa = salvar(justificativa);
        }

        matricula.adicionarJustificativaFalta(justificativa);

        justificativaAplicacaoService.aplicarJustificativa(justificativa);

        return justificativa;
    }

    public JustificativaFalta atualizarJustificativa(JustificativaFaltaRequestDTO dto, JustificativaFalta justificativa) {
        LocalDate DataInicioAtual = justificativa.getDataInicio();
        Integer qtdeDiasAtual = justificativa.getQtdeDias();
        MotivoJustificativa motivoAtual = justificativa.getMotivo();
        boolean mudouPeriodo = false;
        boolean mudouMotivo = false;

        if (!Objects.equals(DataInicioAtual, dto.dataInicio()) || !Objects.equals(qtdeDiasAtual, dto.qtdeDias())) {
            justificativa.setDataInicio(dto.dataInicio());
            justificativa.setQtdeDias(dto.qtdeDias());
            justificativa.setDataFim(dto.dataInicio(), dto.qtdeDias());
            mudouPeriodo = true;
        }

        if (!Objects.equals(motivoAtual, dto.motivo())) {
            justificativa.setMotivo(dto.motivo());
            mudouMotivo = true;
        }

        justificativa.setObservacao(dto.observacao());

        if (mudouPeriodo || mudouMotivo) {
            justificativaAplicacaoService.reAplicarJustificativa(justificativa);
        }

        return justificativa;
    }
}
