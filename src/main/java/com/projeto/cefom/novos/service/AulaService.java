package com.projeto.cefom.novos.service;

import com.projeto.cefom.exceptions.RecursoNaoEncontradoException;
import com.projeto.cefom.exceptions.RegraNegocioException;
import com.projeto.cefom.novos.dto.request.AulaRequestDTO;
import com.projeto.cefom.novos.dto.response.AulaListarResponseDTO;
import com.projeto.cefom.novos.dto.response.AulaResponseDTO;
import com.projeto.cefom.novos.mapper.AulaMapper;
import com.projeto.cefom.novos.model.Aula;
import com.projeto.cefom.novos.model.Lecionamento;
import com.projeto.cefom.novos.model.SalaAula;
import com.projeto.cefom.novos.model.Turma;
import com.projeto.cefom.novos.repository.AulaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class AulaService {
    private final AulaRepository aulaRepository;
    private final AulaMapper aulaMapper;
    private final SalaAulaService salaAulaService;
    private final TurmaService turmaService;
    private final LecionamentoService lecionamentoService;

    public AulaService(AulaRepository aulaRepository, AulaMapper aulaMapper, SalaAulaService salaAulaService, TurmaService turmaService, LecionamentoService lecionamentoService) {
        this.aulaRepository = aulaRepository;
        this.aulaMapper = aulaMapper;
        this.salaAulaService = salaAulaService;
        this.turmaService = turmaService;
        this.lecionamentoService = lecionamentoService;
    }

    @Transactional
    public AulaResponseDTO criar(AulaRequestDTO dto) {
        if (!dto.horarioInicio().isBefore(dto.horarioFim())) {
            throw new RegraNegocioException("Horário de início deve ser menor que o horário de fim.");
        }

        if (aulaRepository.existsConflitoHorarioProfessor(dto.dataAula(),dto.horarioInicio(),dto.horarioFim(),dto.idProfessor())) {
            throw new RegraNegocioException("Professor já possui aula nesse horário.");
        }

        if (aulaRepository.existsConflitoHorarioSalaAula(dto.dataAula(),dto.horarioInicio(),dto.horarioFim(),dto.idSalaAula())) {
            throw new RegraNegocioException("Sala de Aula já está ocupada nesse horário.");
        }

        if (aulaRepository.existsConflitoHorarioTurma(dto.dataAula(),dto.horarioInicio(),dto.horarioFim(),dto.idTurma())) {
            throw new RegraNegocioException("Turma já possui aula nesse horário.");
        }

        Aula aula = salvar(criarAula(dto));
        return aulaMapper.toResponseDTO(aula);
    }

    @Transactional
    public AulaResponseDTO atualizar(Integer idAula, AulaRequestDTO dto) {

        if (!dto.horarioInicio().isBefore(dto.horarioFim())) {
            throw new RegraNegocioException("Horário de início deve ser menor que o horário de fim.");
        }

        if (aulaRepository.existsConflitoHorarioProfessorAndIdAulaNot(dto.dataAula(),dto.horarioInicio(),dto.horarioFim(),dto.idProfessor(),idAula)) {
            throw new RegraNegocioException("Professor já possui aula nesse horário.");
        }

        if (aulaRepository.existsConflitoHorarioSalaAulaAndIdAulaNot(dto.dataAula(),dto.horarioInicio(),dto.horarioFim(),dto.idSalaAula(),idAula)) {
            throw new RegraNegocioException("Sala de Aula já está ocupada nesse horário.");
        }

        if (aulaRepository.existsConflitoHorarioTurmaAndIdAulaNot(dto.dataAula(),dto.horarioInicio(),dto.horarioFim(),dto.idTurma(),idAula)) {
            throw new RegraNegocioException("Turma já possui aula nesse horário.");
        }

        Aula aula = buscarAula(idAula);
        aula = salvar(atualizarAula(dto, aula));
        return aulaMapper.toResponseDTO(aula);
    }

    @Transactional(readOnly = true)
    public AulaResponseDTO buscarPorId(Integer idAula) {
        Aula aula = buscarAula(idAula);
        return aulaMapper.toResponseDTO(aula);
    }

    @Transactional(readOnly = true)
    public Page<AulaListarResponseDTO> listar(Pageable pageable, String disciplina) {
        if (disciplina == null || disciplina.isBlank()) {
            return aulaRepository.findAll(pageable)
                    .map(aulaMapper::toListarResponseDTO);
        }
        return aulaRepository.findByLecionamentoDisciplinaNomeContainingIgnoreCase(disciplina, pageable)
                .map(aulaMapper::toListarResponseDTO);
    }

    @Transactional
    public void excluirPorId(Integer idAula) {
        Aula aula = buscarAula(idAula);
        aulaRepository.deleteById(idAula);
    }

    public Aula salvar(Aula aula) {
        return aulaRepository.save(aula);
    }

    public Aula buscarAula(Integer idAula) {
        Aula aula = aulaRepository.findById(idAula)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aula com ID "+idAula+" não encontrada."));
        return aula;
    }

    public Aula criarAula(AulaRequestDTO dto) {
        Aula aula = new Aula();

        aula.setDataInicio(dto.dataAula());
        aula.setDataFim(dto.dataAula());
        aula.setHorarioInicio(dto.horarioInicio());
        aula.setHorarioFim(dto.horarioFim());

        if (aula.getIdAula() == null) {
            aula = salvar(aula);
        }

        SalaAula sala = salaAulaService.buscarSalaAula(dto.idSalaAula());
        Turma turma = turmaService.buscarTurma(dto.idTurma());
        Lecionamento lecionamento =  lecionamentoService.buscarLecionamento(dto.idProfessor(),dto.idDisciplina());

        sala.adicionarAula(aula);
        turma.adicionarAula(aula);
        lecionamento.adicionarAula(aula);

        return aula;
    }

    public Aula atualizarAula(AulaRequestDTO dto, Aula aula) {

        aula.setDataInicio(dto.dataAula());
        aula.setDataFim(dto.dataAula());
        aula.setHorarioInicio(dto.horarioInicio());
        aula.setHorarioFim(dto.horarioFim());

        if (!Objects.equals(aula.getSala().getIdSalaAula(), dto.idSalaAula())) {
            SalaAula salaAtual = aula.getSala();
            SalaAula salaNova = salaAulaService.buscarSalaAula(dto.idSalaAula());

            salaAtual.removerAula(aula);
            salaNova.adicionarAula(aula);
        }

        if (!Objects.equals(aula.getTurma().getIdTurma(), dto.idTurma())) {
            if (aula.getChamadaRealizada()) {
                throw new RegraNegocioException("Não é possivel alterar a Turma após realizar a chamada.");
            }

            Turma turmaAtual = aula.getTurma();
            Turma turmaNova = turmaService.buscarTurma(dto.idTurma());

            turmaAtual.removerAula(aula);
            turmaNova.adicionarAula(aula);
        }

        if (!Objects.equals(aula.getLecionamento().getDisciplina().getIdDisciplina(), dto.idDisciplina()) ||
                !Objects.equals(aula.getLecionamento().getProfessor().getIdProfessor(), dto.idProfessor())) {

            if (aula.getChamadaRealizada() &&
                    !Objects.equals(aula.getLecionamento().getProfessor().getIdProfessor(), dto.idProfessor())) {
                throw new RegraNegocioException("Não é possivel alterar o Professor após realizar a chamada.");
            }

            Lecionamento lecionamentoAtual = aula.getLecionamento();
            Lecionamento lecionamentoNovo = lecionamentoService.buscarLecionamento(dto.idProfessor(),dto.idDisciplina());

            lecionamentoAtual.removerAula(aula);
            if (!aulaRepository.existsByLecionamentoAndIdAulaNot(lecionamentoAtual, aula.getIdAula())) {
                lecionamentoService.deletarLecionamento(lecionamentoAtual);
            }

            lecionamentoNovo.adicionarAula(aula);
        }

        return aula;
    }
}
