package com.projeto.sistema.service;

import com.projeto.sistema.exceptions.RecursoNaoEncontradoException;
import com.projeto.sistema.exceptions.RegraNegocioException;
import com.projeto.sistema.dto.request.SalarioAtualizarRequestDTO;
import com.projeto.sistema.dto.response.SalarioListarResponseDTO;
import com.projeto.sistema.dto.response.SalarioResponseDTO;
import com.projeto.sistema.mapper.SalarioMapper;
import com.projeto.sistema.model.Contrato;
import com.projeto.sistema.model.Salario;
import com.projeto.sistema.repository.SalarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class SalarioService {

    private final ContratoService contratoService;
    private final SalarioRepository salarioRepository;
    private final SalarioMapper salarioMapper;

    public SalarioService(ContratoService contratoService, SalarioRepository salarioRepository, SalarioMapper salarioMapper) {
        this.contratoService = contratoService;
        this.salarioRepository = salarioRepository;
        this.salarioMapper = salarioMapper;
    }

    @Transactional
    public SalarioResponseDTO atualizar(Integer idAdolescente, Integer idMatricula, Integer idContrato, SalarioAtualizarRequestDTO dto) {
        LocalDate dataAtualizacao = dto.dataModificacao();
        if(dataAtualizacao == null) {
            dataAtualizacao = LocalDate.now();
        }

        Contrato contrato = contratoService.buscarContratoMatricula(idAdolescente, idMatricula, idContrato);

        atualizarSalario(contrato,dto,dataAtualizacao);

        contratoService.salvar(contrato);

        return salarioMapper.toResponseDTO(contrato, dataAtualizacao);
    }


    @Transactional(readOnly = true)
    public List<SalarioListarResponseDTO> listar(Integer idAdolescente, Integer idMatricula, Integer idContrato) {
        contratoService.buscarContratoMatricula(idAdolescente,idMatricula,idContrato);
        return salarioRepository.findAllByContratoIdContrato(idContrato)
                .stream()
                .map(salarioMapper::toListarResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public SalarioResponseDTO buscarPorId(Integer idAdolescente, Integer idMatricula, Integer idContrato, Integer idSalario) {
        Salario salario = buscarSalarioContrato(idAdolescente,idMatricula,idContrato,idSalario);
        return salarioMapper.toResponseDTO(salario);
    }

    @Transactional
    public void excluirPorId(Integer idAdolescente, Integer idMatricula, Integer idContrato, Integer idSalario) {
        buscarSalarioContrato(idAdolescente,idMatricula,idContrato,idSalario);
        salarioRepository.deleteById(idSalario);
    }

    public Salario buscarSalario(Integer idSalario) {
        Salario salario = salarioRepository.findById(idSalario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Salário com ID "+idSalario+" não encontrado."));
        return salario;
    }

    public Salario buscarSalarioContrato(Integer idAdolescente, Integer idMatricula, Integer idContrato, Integer idSalario) {
        contratoService.buscarContratoMatricula(idAdolescente,idMatricula,idContrato);
        Salario salario = buscarSalario(idSalario);

        if (salario.getContrato() == null) {
            throw new RegraNegocioException("Salário não pertence a um contrato.");
        }

        if (!salario.getContrato().getIdContrato().equals(idContrato)) {
            throw new RegraNegocioException("Salário não pertence ao contrato.");
        }

        return salario;
    }

    public void criarSalario(Contrato contrato, SalarioAtualizarRequestDTO dto, LocalDate data) {
        Salario salario = new Salario();

        salario.setValorBase(dto.valorBase());
        salario.setTipoSalario(dto.tipoSalario());
        salario.setDataInicio(data);

        contrato.adicionarSalario(salario);
    }

    public boolean salarioIgual(Salario s, SalarioAtualizarRequestDTO dto) {

        return Objects.equals(s.getValorBase(),dto.valorBase()) &&
                Objects.equals(s.getTipoSalario(), dto.tipoSalario());
    }

    public void atualizarSalario(Contrato contrato, SalarioAtualizarRequestDTO dto, LocalDate data) {

        Salario salarioAtual = contrato.getSalarios().stream()
                .filter(e -> e.getDataFim() == null)
                .max(Comparator.comparing(Salario::getDataInicio))
                .orElse(null);

        if (salarioAtual != null && salarioIgual(salarioAtual, dto)) {
            return;
        }

        if (salarioAtual != null) {
            salarioAtual.setDataFim(data);
        }

        criarSalario(contrato,dto,data);
    }
}
