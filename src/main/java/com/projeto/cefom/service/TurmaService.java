package com.projeto.cefom.service;

import com.projeto.cefom.exceptions.RecursoNaoEncontradoException;
import com.projeto.cefom.exceptions.RegraNegocioException;
import com.projeto.cefom.dto.request.AlunoParticipacaoRequestDTO;
import com.projeto.cefom.dto.request.TurmaRequestDTO;
import com.projeto.cefom.dto.response.TurmaListarResponseDTO;
import com.projeto.cefom.dto.response.TurmaResponseDTO;
import com.projeto.cefom.dto.response.TurmaSelectResponseDTO;
import com.projeto.cefom.mapper.TurmaMapper;
import com.projeto.cefom.model.Turma;
import com.projeto.cefom.repository.TurmaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class TurmaService {

    private final TurmaRepository turmaRepository;
    private final TurmaMapper turmaMapper;
    private final ParticipacaoService participacaoService;

    public TurmaService(TurmaRepository turmaRepository, TurmaMapper turmaMapper, ParticipacaoService participacaoService) {
        this.turmaRepository = turmaRepository;
        this.turmaMapper = turmaMapper;
        this.participacaoService = participacaoService;
    }

    @Transactional
    public TurmaResponseDTO criar(TurmaRequestDTO dto) {
        if (turmaRepository.existsByNome(dto.nome())) {
            throw new RegraNegocioException("Turma já cadastrada.");
        }
        Turma turma = salvar(criarTurma(dto));
        return turmaMapper.toResponseDTO(turma, LocalDate.now());
    }

    @Transactional
    public TurmaResponseDTO atualizar(Integer idTurma, TurmaRequestDTO dto) {
        if (turmaRepository.existsByNomeAndIdTurmaNot(dto.nome(), idTurma)) {
            throw new RegraNegocioException("Turma já cadastrada.");
        }
        Turma turma = buscarTurma(idTurma);
        turma = salvar(atualizarTurma(dto, turma));
        return turmaMapper.toResponseDTO(turma, LocalDate.now());
    }

    @Transactional(readOnly = true)
    public TurmaResponseDTO buscarPorId(Integer idTurma) {
        Turma turma = buscarTurma(idTurma);
        return turmaMapper.toResponseDTO(turma, LocalDate.now());
    }

    @Transactional(readOnly = true)
    public Page<TurmaListarResponseDTO> listar(Pageable pageable, String nome) {
        if (nome == null || nome.isBlank()) {
            return turmaRepository.findAll(pageable)
                    .map(turmaMapper::toListarResponseDTO);
        }
        return turmaRepository.findByNomeContainingIgnoreCase(nome, pageable)
                .map(turmaMapper::toListarResponseDTO);
    }

    @Transactional(readOnly = true)
    public List<TurmaSelectResponseDTO> listarSelect() {
        return turmaRepository.findAll()
                .stream()
                .map(turmaMapper::toSelectResponseDTO)
                .toList();
    }

    @Transactional
    public void excluirPorId(Integer idTurma) {
        Turma turma = buscarTurma(idTurma);
        turmaRepository.deleteById(idTurma);
    }

    @Transactional
    public TurmaResponseDTO adicionarAdolescente(Integer idTurma, AlunoParticipacaoRequestDTO dto) {
        LocalDate dataAdicionar = dto.dataModificacao();
        if(dataAdicionar == null) {
            dataAdicionar = LocalDate.now();
        }

        Turma turma = buscarTurma(idTurma);

        participacaoService.adicionarTurmaAluno(turma, dto.idMatricula(), dataAdicionar);

        turma = salvar(turma);

        return turmaMapper.toResponseDTO(turma, LocalDate.now());
    }

    @Transactional
    public TurmaResponseDTO removerAdolescente(Integer idTurma, AlunoParticipacaoRequestDTO dto) {
        LocalDate dataRemover = dto.dataModificacao();
        if(dataRemover == null) {
            dataRemover = LocalDate.now();
        }

        Turma turma = buscarTurma(idTurma);

        participacaoService.removerTurmaAluno(turma, dto.idMatricula(), dataRemover);

        turma = salvar(turma);

        return turmaMapper.toResponseDTO(turma, LocalDate.now());
    }

    public Turma salvar(Turma turma) {
        return turmaRepository.save(turma);
    }

    public Turma buscarTurma(Integer idTurma) {
        Turma turma = turmaRepository.findById(idTurma)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Turma com ID "+idTurma+" não encontrada."));
        return turma;
    }

    public Turma criarTurma(TurmaRequestDTO dto) {
        Turma turma = new Turma();
        turma.setNome(dto.nome());
        turma.setTipo(dto.tipo());
        return turma;
    }

    public Turma atualizarTurma(TurmaRequestDTO dto, Turma turma) {
        turma.setNome(dto.nome());
        turma.setTipo(dto.tipo());
        return turma;
    }
}
