package com.projeto.cefom.service;

import com.projeto.cefom.exceptions.RecursoNaoEncontradoException;
import com.projeto.cefom.exceptions.RegraNegocioException;
import com.projeto.cefom.model.Contrato;
import com.projeto.cefom.dto.request.FaltaTrabalhoRequestDTO;
import com.projeto.cefom.dto.response.FaltaTrabalhoListarResponseDTO;
import com.projeto.cefom.dto.response.FaltaTrabalhoResponseDTO;
import com.projeto.cefom.mapper.FaltaTrabalhoMapper;
import com.projeto.cefom.model.FaltaTrabalho;
import com.projeto.cefom.repository.FaltaTrabalhoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class FaltaTrabalhoService {

    private final FaltaTrabalhoRepository faltaTrabalhoRepository;
    private final FaltaTrabalhoMapper faltaTrabalhoMapper;
    private final ContratoService contratoService;
    private final JustificativaAplicacaoService justificativaAplicacaoService;

    public FaltaTrabalhoService(FaltaTrabalhoRepository faltaTrabalhoRepository, FaltaTrabalhoMapper faltaTrabalhoMapper, ContratoService contratoService, JustificativaAplicacaoService justificativaAplicacaoService) {
        this.faltaTrabalhoRepository = faltaTrabalhoRepository;
        this.faltaTrabalhoMapper = faltaTrabalhoMapper;
        this.contratoService = contratoService;
        this.justificativaAplicacaoService = justificativaAplicacaoService;
    }

    @Transactional
    public FaltaTrabalhoResponseDTO criar(Integer idAdolescente, Integer idMatricula, Integer idContrato, FaltaTrabalhoRequestDTO dto) {
        Contrato contrato = contratoService.buscarContratoMatricula(idAdolescente,idMatricula,idContrato);

        if (faltaTrabalhoRepository.existsByContratoIdContratoAndDataInicio(idContrato, dto.dataFalta())) {
            throw new RegraNegocioException("Falta no Trabalho já cadastrada.");
        }

        FaltaTrabalho falta = salvar(criarFaltaTrabalho(dto, contrato));
        return faltaTrabalhoMapper.toResponseDTO(falta);
    }

    @Transactional
    public FaltaTrabalhoResponseDTO atualizar(Integer idAdolescente, Integer idMatricula, Integer idContrato, Integer idFaltaTrabalho, FaltaTrabalhoRequestDTO dto) {
        Contrato contrato = contratoService.buscarContratoMatricula(idAdolescente,idMatricula,idContrato);

        if (faltaTrabalhoRepository.existsByContratoIdContratoAndDataInicioAndIdFaltaTrabalhoNot(idContrato, dto.dataFalta(), idFaltaTrabalho)) {
            throw new RegraNegocioException("Falta no Trabalho já cadastrada.");
        }

        FaltaTrabalho falta = buscarFaltaTrabalho(idFaltaTrabalho);
        falta = salvar(atualizarFaltaTrabalho(dto, falta));
        return faltaTrabalhoMapper.toResponseDTO(falta);
    }

    @Transactional(readOnly = true)
    public Page<FaltaTrabalhoListarResponseDTO> listar(Integer idAdolescente, Integer idMatricula, Integer idContrato, Pageable pageable) {
        contratoService.buscarContratoMatricula(idAdolescente,idMatricula,idContrato);
        return faltaTrabalhoRepository.findAllByContratoIdContrato(idContrato, pageable)
                .map(faltaTrabalhoMapper::toListarResponseDTO);
    }

    @Transactional(readOnly = true)
    public FaltaTrabalhoResponseDTO buscarPorId(Integer idAdolescente, Integer idMatricula, Integer idContrato, Integer idFaltaTrabalho) {
        FaltaTrabalho falta = buscarFaltaTrabalhoContrato(idAdolescente,idMatricula,idContrato,idFaltaTrabalho);
        return faltaTrabalhoMapper.toResponseDTO(falta);
    }

    @Transactional
    public void excluirPorId(Integer idAdolescente, Integer idMatricula, Integer idContrato, Integer idFaltaTrabalho) {
        FaltaTrabalho falta = buscarFaltaTrabalhoContrato(idAdolescente,idMatricula,idContrato,idFaltaTrabalho);
        faltaTrabalhoRepository.deleteById(idFaltaTrabalho);
    }

    public FaltaTrabalho salvar(FaltaTrabalho falta) {
        return faltaTrabalhoRepository.save(falta);
    }

    public FaltaTrabalho buscarFaltaTrabalho(Integer idFaltaTrabalho) {
        FaltaTrabalho falta = faltaTrabalhoRepository.findById(idFaltaTrabalho)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Falta no Trabalho com ID "+idFaltaTrabalho+" não encontrada."));
        return falta;
    }

    public FaltaTrabalho buscarFaltaTrabalhoContrato(Integer idAdolescente, Integer idMatricula, Integer idContrato, Integer idFaltaTrabalho) {
        contratoService.buscarContratoMatricula(idAdolescente,idMatricula,idContrato);
        FaltaTrabalho falta = buscarFaltaTrabalho(idFaltaTrabalho);

        if (falta.getContrato() == null) {
            throw new RegraNegocioException("Falta no Trabalho não pertence a um contrato.");
        }

        if (!falta.getContrato().getIdContrato().equals(idContrato)) {
            throw new RegraNegocioException("Falta no Trabalho não pertence ao contrato.");
        }

        return falta;
    }

    public FaltaTrabalho criarFaltaTrabalho(FaltaTrabalhoRequestDTO dto, Contrato contrato) {
        FaltaTrabalho falta = new FaltaTrabalho();
        falta.setDataInicio(dto.dataFalta());
        falta.setDataFim(dto.dataFalta());

        if (falta.getIdFaltaTrabalho() == null) {
            falta = salvar(falta);
        }

        contrato.adicionarFaltaTrabalho(falta);

        justificativaAplicacaoService.aplicarJustificativa(falta);

        return falta;
    }

    public FaltaTrabalho atualizarFaltaTrabalho(FaltaTrabalhoRequestDTO dto, FaltaTrabalho falta) {
        LocalDate dataAtual = falta.getDataInicio();

        if (!Objects.equals(dataAtual, dto.dataFalta())) {
            falta.setDataInicio(dto.dataFalta());
            falta.setDataFim(dto.dataFalta());

            justificativaAplicacaoService.reAplicarJustificativa(falta);
        }

        return falta;
    }
}
