package com.projeto.sistema.service;

import com.projeto.sistema.exceptions.RecursoNaoEncontradoException;
import com.projeto.sistema.exceptions.RegraNegocioException;
import com.projeto.sistema.dto.request.JornadaTrabalhoAtualizarRequestDTO;
import com.projeto.sistema.dto.response.JornadaTrabalhoListarResponseDTO;
import com.projeto.sistema.dto.response.JornadaTrabalhoResponseDTO;
import com.projeto.sistema.enums.DiaSemana;
import com.projeto.sistema.mapper.JornadaTrabalhoMapper;
import com.projeto.sistema.model.Contrato;
import com.projeto.sistema.model.JornadaTrabalho;
import com.projeto.sistema.repository.JornadaTrabalhoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class JornadaTrabalhoService {

    private final JornadaTrabalhoRepository jornadaTrabalhoRepository;
    private final JornadaTrabalhoMapper jornadaTrabalhoMapper;
    private final ContratoService contratoService;

    public JornadaTrabalhoService(JornadaTrabalhoRepository jornadaTrabalhoRepository, JornadaTrabalhoMapper jornadaTrabalhoMapper, ContratoService contratoService) {
        this.jornadaTrabalhoRepository = jornadaTrabalhoRepository;
        this.jornadaTrabalhoMapper = jornadaTrabalhoMapper;
        this.contratoService = contratoService;
    }

    @Transactional
    public JornadaTrabalhoResponseDTO atualizar(Integer idAdolescente, Integer idMatricula, Integer idContrato, JornadaTrabalhoAtualizarRequestDTO dto) {
        LocalDate dataAtualizacao = dto.dataModificacao();
        if(dataAtualizacao == null) {
            dataAtualizacao = LocalDate.now();
        }

        Contrato contrato = contratoService.buscarContratoMatricula(idAdolescente, idMatricula, idContrato);

        atualizarJornadaTrabalho(contrato,dto,dataAtualizacao);

        contratoService.salvar(contrato);

        return jornadaTrabalhoMapper.toResponseDTO(contrato, dataAtualizacao);
    }


    @Transactional(readOnly = true)
    public List<JornadaTrabalhoListarResponseDTO> listar(Integer idAdolescente, Integer idMatricula, Integer idContrato) {
        contratoService.buscarContratoMatricula(idAdolescente,idMatricula,idContrato);
        return jornadaTrabalhoRepository.findAllByContratoIdContrato(idContrato)
                .stream()
                .map(jornadaTrabalhoMapper::toListarResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public JornadaTrabalhoResponseDTO buscarPorId(Integer idAdolescente, Integer idMatricula, Integer idContrato, Integer idJornadaTrabalho) {
        JornadaTrabalho jornada = buscarJornadaTrabalhoContrato(idAdolescente,idMatricula,idContrato,idJornadaTrabalho);
        return jornadaTrabalhoMapper.toResponseDTO(jornada);
    }

    @Transactional
    public void excluirPorId(Integer idAdolescente, Integer idMatricula, Integer idContrato, Integer idJornadaTrabalho) {
        buscarJornadaTrabalhoContrato(idAdolescente,idMatricula,idContrato,idJornadaTrabalho);
        jornadaTrabalhoRepository.deleteById(idJornadaTrabalho);
    }

    public JornadaTrabalho buscarJornadaTrabalho(Integer idJornadaTrabalho) {
        JornadaTrabalho jornada = jornadaTrabalhoRepository.findById(idJornadaTrabalho)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Jornada de trabalho com ID "+idJornadaTrabalho+" não encontrada."));
        return jornada;
    }

    public JornadaTrabalho buscarJornadaTrabalhoContrato(Integer idAdolescente, Integer idMatricula, Integer idContrato, Integer idJornadaTrabalho) {
        contratoService.buscarContratoMatricula(idAdolescente,idMatricula,idContrato);
        JornadaTrabalho jornada = buscarJornadaTrabalho(idJornadaTrabalho);

        if (jornada.getContrato() == null) {
            throw new RegraNegocioException("Jornada de trabalho não pertence a um contrato.");
        }

        if (!jornada.getContrato().getIdContrato().equals(idContrato)) {
            throw new RegraNegocioException("Jornada de trabalho não pertence ao contrato.");
        }

        return  jornada;
    }

    public void criarJornadaTrabalho(Contrato contrato, JornadaTrabalhoAtualizarRequestDTO dto, LocalDate data) {
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

    public boolean jornadaTrabalhoIgual(JornadaTrabalho j, JornadaTrabalhoAtualizarRequestDTO dto) {
        DiaSemana folga = dto.diaFolga();
        if(folga == null) {
            folga = DiaSemana.DOMINGO;
        }

        return Objects.equals(j.getCargaHoraria(),dto.cargaHoraria()) &&
        Objects.equals(j.getHorarioSemanaEntrada(), dto.horarioSemanaEntrada()) &&
        Objects.equals(j.getHorarioSemanaSaida(), dto.horarioSemanaSaida()) &&
        Objects.equals(j.getHorarioAlmocoEntrada(), dto.horarioAlmocoEntrada()) &&
        Objects.equals(j.getHorarioAlmocoSaida(), dto.horarioAlmocoSaida()) &&
        Objects.equals(j.getHorarioSabadoEntrada(), dto.horarioSabadoEntrada()) &&
        Objects.equals(j.getHorarioSabadoSaida(), dto.horarioSabadoSaida()) &&
        Objects.equals(j.getDiaCurso(), dto.diaCurso()) &&
        Objects.equals(j.getDiaFolga(), folga);
    }

    public void atualizarJornadaTrabalho(Contrato contrato, JornadaTrabalhoAtualizarRequestDTO dto, LocalDate data) {

        JornadaTrabalho jornadaAtual = contrato.getJornadasTrabalho().stream()
                .filter(e -> e.getDataFim() == null)
                .max(Comparator.comparing(JornadaTrabalho::getDataInicio))
                .orElse(null);

        if (jornadaAtual != null && jornadaTrabalhoIgual(jornadaAtual, dto)) {
            return;
        }

        if (jornadaAtual != null) {
            jornadaAtual.setDataFim(data);
        }

        criarJornadaTrabalho(contrato,dto,data);
    }
}
