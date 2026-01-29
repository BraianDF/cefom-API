package com.projeto.sistema.service;

import com.projeto.sistema.dto.request.*;
import com.projeto.sistema.exceptions.RecursoNaoEncontradoException;
import com.projeto.sistema.exceptions.RegraNegocioException;
import com.projeto.sistema.model.*;
import com.projeto.sistema.dto.response.ContratoListarResponseDTO;
import com.projeto.sistema.dto.response.ContratoResponseDTO;
import com.projeto.sistema.enums.DiaSemana;
import com.projeto.sistema.mapper.ContratoMapper;
import com.projeto.sistema.repository.ContratoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class ContratoService {
    private final ContratoRepository contratoRepository;
    private final ContratoMapper contratoMapper;
    private final EmpresaService empresaService;
    private final VinculoContratoCargoService vinculoContratoCargoService;
    private final MatriculaService matriculaService;

    public ContratoService(ContratoRepository contratoRepository, ContratoMapper contratoMapper, EmpresaService empresaService, VinculoContratoCargoService vinculoContratoCargoService, MatriculaService matriculaService) {
        this.contratoRepository = contratoRepository;
        this.contratoMapper = contratoMapper;
        this.empresaService = empresaService;
        this.vinculoContratoCargoService = vinculoContratoCargoService;
        this.matriculaService = matriculaService;
    }

    @Transactional
    public ContratoResponseDTO criar(Integer idAdolescente, Integer idMatricula, ContratoRequestDTO dto) {
        LocalDate dataCriacao = dto.contrato().DataInicio();

        Matricula matricula = matriculaService.buscarMatriculaAdolescente(idAdolescente, idMatricula);

        Contrato contrato = criarContrato(dto.contrato());

        if (contrato.getIdContrato() == null) {
            contrato = salvar(contrato);
        }

        //Matricula
        matricula.adicionarContrato(contrato);

        //Cargo
        vinculoContratoCargoService.adicionarContratoCargo(contrato,dto.contrato().idCargo(),dataCriacao);

        //Empresa
        Empresa empresa = empresaService.buscarEmpresa(dto.contrato().idEmpresa());
        empresa.adicionarContrato(contrato);

        //Jornada de Trabalho
        criarJornadaTrabalho(contrato, dto.jornadaTrabalho(), dataCriacao);

        //Salario
        criarSalario(contrato, dto.salario(), dataCriacao);

        Contrato contratoSalvo = salvar(contrato);

        return contratoMapper.toResponseDTO(contrato, dataCriacao);
    }

    @Transactional
    public ContratoResponseDTO atualizar(Integer idAdolescente, Integer idMatricula, Integer idContrato, ContratoAtualizarRequestDTO dto) {
        Contrato contrato = buscarContratoMatricula(idAdolescente, idMatricula, idContrato);

        LocalDate dataAtualizacao = dto.dataModificacao();
        if(dataAtualizacao == null) {
            dataAtualizacao = LocalDate.now();
        }

        atualizarContrato(contrato,dto,dataAtualizacao);

        Contrato contratoSalvo = salvar(contrato);

        return contratoMapper.toResponseDTO(contrato, dataAtualizacao);
    }

    @Transactional
    public ContratoResponseDTO encerrar(Integer idAdolescente, Integer idMatricula, Integer idContrato, ContratoEncerrarRequestDTO dto) {
        Contrato contrato = buscarContratoMatricula(idAdolescente, idMatricula, idContrato);

        LocalDate dataDesligamento = dto.dataFim();
        if(dataDesligamento == null) {
            dataDesligamento = LocalDate.now();
        }

        contrato.setDataFim(dataDesligamento);
        contrato.setDesligamento(dto.motivoDesligamento());
        contrato.setEfetivado(dto.efetivado());

        Contrato contratoSalvo = salvar(contrato);

        return contratoMapper.toResponseDTO(contrato, dataDesligamento);
    }

    @Transactional(readOnly = true)
    public Page<ContratoListarResponseDTO> listar(Integer idAdolescente, Pageable pageable) {
        return contratoRepository.findAllByMatriculaAdolescenteIdAdolescente(idAdolescente, pageable)
                .map(contratoMapper::toListarResponseDTO);
    }

    @Transactional(readOnly = true)
    public ContratoResponseDTO buscarPorId(Integer idAdolescente, Integer idMatricula, Integer idContrato) {
        Contrato contrato = buscarContratoMatricula(idAdolescente,idMatricula,idContrato);
        return contratoMapper.toResponseDTO(contrato, LocalDate.now());
    }

    @Transactional
    public void excluirPorId(Integer idAdolescente, Integer idMatricula, Integer idContrato) {
        Contrato contrato = buscarContratoMatricula(idAdolescente,idMatricula,idContrato);
        contratoRepository.deleteById(idContrato);
    }

    public Contrato salvar(Contrato contrato) {
        Contrato salvo = contratoRepository.save(contrato);
        return salvo;
    }

    @Transactional(readOnly = true)
    public Contrato buscarContrato(Integer idContrato) {
        Contrato contrato = contratoRepository.findById(idContrato)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Contrato com ID "+idContrato+" não encontrado."));
        return contrato;
    }

    public Contrato buscarContratoMatricula(Integer idAdolescente, Integer idMatricula, Integer idContrato) {
        Matricula matricula = matriculaService.buscarMatriculaAdolescente(idAdolescente, idMatricula);

        Contrato contrato = buscarContrato(idContrato);

        if (contrato.getMatricula() == null) {
            throw new RegraNegocioException("Contrato não pertence a uma matrícula.");
        }

        if (!contrato.getMatricula().getIdMatricula().equals(idMatricula)) {
            throw new RegraNegocioException("Contrato não pertence a matrícula.");
        }

        return contrato;
    }

    public void atualizarContrato(Contrato contrato, ContratoAtualizarRequestDTO dto, LocalDate data) {
        contrato.setDataInicio(dto.DataInicio());
        contrato.setDataTermino(dto.dataTermino());
        contrato.setTipoContratacao(dto.tipoContratacao());

        VinculoContratoCargo cargoAtual = contrato.getCargos()
                .stream()
                .filter(e -> e.estaValidoEm(data))
                .max(Comparator.comparing(VinculoContratoCargo::getDataInicio))
                .orElse(null);

        if(!cargoAtual.getCargo().getIdCargo().equals(dto.idCargo())){
            vinculoContratoCargoService.encerrarContratoCargo(contrato,cargoAtual.getCargo().getIdCargo(),data);
            vinculoContratoCargoService.adicionarContratoCargo(contrato,dto.idCargo(),data);
        }

    }

    public Contrato criarContrato(ContratoCriarRequestDTO dto) {
        Contrato contrato = new Contrato();

        contrato.setDataInicio(dto.DataInicio());
        contrato.setDataTermino(dto.dataTermino());
        contrato.setTipoContratacao(dto.tipoContratacao());

        return contrato;
    }

    public void criarSalario(Contrato contrato, SalarioRequestDTO dto, LocalDate data) {
        Salario salario = new Salario();

        salario.setValorBase(dto.valorBase());
        salario.setTipoSalario(dto.tipoSalario());
        salario.setDataInicio(data);

        contrato.adicionarSalario(salario);
    }

    public void criarJornadaTrabalho(Contrato contrato, JornadaTrabalhoRequestDTO dto, LocalDate data) {
        JornadaTrabalho jornada = new JornadaTrabalho();

        DiaSemana folga = dto.diaFolga();
        if(folga == null) {
            folga = DiaSemana.DOMINGO;
        }

        jornada.setCargaHoraria(dto.cargaHoraria());
        jornada.setHorarioSemanaEntrada(dto.horarioSemanaEntrada());
        jornada.setHorarioSemanaSaida(dto.horarioSemanaSaida());
        jornada.setHorarioAlmocoEntrada(dto.horarioAlmocoEntrada());
        jornada.setHorarioAlmocoSaida(dto.horarioAlmocoSaida());
        jornada.setHorarioSabadoEntrada(dto.horarioSabadoEntrada());
        jornada.setHorarioSabadoSaida(dto.horarioSabadoSaida());
        jornada.setDiaCurso(dto.diaCurso());
        jornada.setDiaFolga(folga);
        jornada.setDataInicio(data);

        contrato.adicionarJornadaTrabalho(jornada);
    }

}
