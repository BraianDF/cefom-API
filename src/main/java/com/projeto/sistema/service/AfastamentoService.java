package com.projeto.sistema.service;

import com.projeto.sistema.exceptions.RecursoNaoEncontradoException;
import com.projeto.sistema.exceptions.RegraNegocioException;
import com.projeto.sistema.dto.request.AfastamentoRequestDTO;
import com.projeto.sistema.dto.response.AfastamentoListarResponseDTO;
import com.projeto.sistema.dto.response.AfastamentoResponseDTO;
import com.projeto.sistema.enums.MotivoAfastamento;
import com.projeto.sistema.mapper.AfastamentoMapper;
import com.projeto.sistema.model.Afastamento;
import com.projeto.sistema.model.Contrato;
import com.projeto.sistema.repository.AfastamentoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class AfastamentoService {
    private final AfastamentoRepository afastamentoRepository;
    private final AfastamentoMapper afastamentoMapper;
    private final ContratoService contratoService;

    public AfastamentoService(AfastamentoRepository afastamentoRepository, AfastamentoMapper afastamentoMapper, ContratoService contratoService) {
        this.afastamentoRepository = afastamentoRepository;
        this.afastamentoMapper = afastamentoMapper;
        this.contratoService = contratoService;
    }

    @Transactional
    public AfastamentoResponseDTO criar(Integer idAdolescente, Integer idMatricula, Integer idContrato, AfastamentoRequestDTO dto) {
        Contrato contrato = contratoService.buscarContratoMatricula(idAdolescente, idMatricula, idContrato);

        if (afastamentoRepository.existsByDataInicioAndDataFim(dto.dataInicio(), dto.dataTermino())) {
            throw new RegraNegocioException("Ja existe um afastamento nesse periodo de tempo para esse contrato.");
        }

        criarAfastamento(contrato, dto);

        contratoService.salvar(contrato);

        return afastamentoMapper.toResponseDTO(contrato, dto.dataInicio());
    }

    @Transactional
    public AfastamentoResponseDTO atualizar(Integer idAdolescente, Integer idMatricula, Integer idContrato, Integer idAfastamento, AfastamentoRequestDTO dto) {
        Afastamento afastamento = buscarAfastamentoContrato(idAdolescente, idMatricula, idContrato, idAfastamento);

        Contrato contrato = contratoService.buscarContratoMatricula(idAdolescente, idMatricula, idContrato);

        if (afastamentoRepository.existsByDataInicioAndDataFimAndIdAfastamentoNot(dto.dataInicio(), dto.dataTermino(), idAfastamento)) {
            throw new RegraNegocioException("Ja existe um afastamento nesse periodo de tempo para esse contrato.");
        }

        atualizarAfastamento(afastamento, dto);

        contratoService.salvar(contrato);

        return afastamentoMapper.toResponseDTO(contrato, dto.dataInicio());
    }


    @Transactional(readOnly = true)
    public Page<AfastamentoListarResponseDTO> listar(Integer idAdolescente, Integer idMatricula, Integer idContrato, Pageable pageable) {
        contratoService.buscarContratoMatricula(idAdolescente,idMatricula,idContrato);
        return afastamentoRepository.findAllByContratoIdContrato(idContrato, pageable)
                .map(afastamentoMapper::toListarResponseDTO);
    }

    @Transactional(readOnly = true)
    public AfastamentoResponseDTO buscarPorId(Integer idAdolescente, Integer idMatricula, Integer idContrato, Integer idAfastamento) {
        Afastamento afastamento = buscarAfastamentoContrato(idAdolescente,idMatricula,idContrato,idAfastamento);
        return afastamentoMapper.toResponseDTO(afastamento);
    }

    @Transactional
    public void excluirPorId(Integer idAdolescente, Integer idMatricula, Integer idContrato, Integer idAfastamento) {
        buscarAfastamentoContrato(idAdolescente,idMatricula,idContrato,idAfastamento);
        afastamentoRepository.deleteById(idAfastamento);
    }

    public Afastamento buscarAfastamento(Integer idAfastamento) {
        Afastamento afastamento = afastamentoRepository.findById(idAfastamento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Afastamento com ID "+idAfastamento+" não encontrado."));
        return afastamento;
    }

    public Afastamento buscarAfastamentoContrato(Integer idAdolescente, Integer idMatricula, Integer idContrato, Integer idAfastamento) {
        contratoService.buscarContratoMatricula(idAdolescente,idMatricula,idContrato);
        Afastamento afastamento = buscarAfastamento(idAfastamento);

        if (afastamento.getContrato() == null) {
            throw new RegraNegocioException("Afastamento não pertence a um contrato.");
        }

        if (!afastamento.getContrato().getIdContrato().equals(idContrato)) {
            throw new RegraNegocioException("Afastamento não pertence ao contrato.");
        }

        return afastamento;
    }

    public void criarAfastamento(Contrato contrato, AfastamentoRequestDTO dto) {
        String outroMotivo = null;
        if (dto.motivoAfastamento() == MotivoAfastamento.OUTROS) {
            outroMotivo = dto.outroMotivoAfastamento();
        }

        Afastamento afastamento = new Afastamento();

        afastamento.setDataInicio(dto.dataInicio());
        afastamento.setDataFim(dto.dataTermino());
        afastamento.setQtdeDias(dto.qtdeDias());
        afastamento.setMotivoAfastamento(dto.motivoAfastamento());
        afastamento.setOutroMotivoAfastamento(outroMotivo);
        afastamento.setObservacao(dto.observacao());

        contrato.adicionarAfastamento(afastamento);
    }

    public boolean afastamentoIgual(Afastamento a, AfastamentoRequestDTO dto) {
        String outroMotivo = null;
        if (dto.motivoAfastamento() == MotivoAfastamento.OUTROS) {
             outroMotivo = dto.outroMotivoAfastamento();
        }

        return Objects.equals(a.getDataInicio(),dto.dataInicio()) &&
                Objects.equals(a.getDataFim(), dto.dataTermino()) &&
                Objects.equals(a.getQtdeDias(), dto.qtdeDias()) &&
                Objects.equals(a.getMotivoAfastamento(), dto.motivoAfastamento()) &&
                Objects.equals(a.getOutroMotivoAfastamento(), outroMotivo) &&
                Objects.equals(a.getObservacao(), dto.observacao());
    }

    public void atualizarAfastamento(Afastamento afastamentoAtual, AfastamentoRequestDTO dto) {
        if (afastamentoAtual != null && afastamentoIgual(afastamentoAtual, dto)) {
            return;
        }

        String outroMotivo = null;
        if (dto.motivoAfastamento() == MotivoAfastamento.OUTROS) {
            outroMotivo = dto.outroMotivoAfastamento();
        }

        afastamentoAtual.setDataInicio(dto.dataInicio());
        afastamentoAtual.setDataFim(dto.dataTermino());
        afastamentoAtual.setQtdeDias(dto.qtdeDias());
        afastamentoAtual.setMotivoAfastamento(dto.motivoAfastamento());
        afastamentoAtual.setOutroMotivoAfastamento(outroMotivo);
        afastamentoAtual.setObservacao(dto.observacao());

    }
}
