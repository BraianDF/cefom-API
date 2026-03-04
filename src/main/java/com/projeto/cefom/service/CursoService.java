package com.projeto.cefom.service;

import com.projeto.cefom.exceptions.RecursoNaoEncontradoException;
import com.projeto.cefom.exceptions.RegraNegocioException;
import com.projeto.cefom.dto.request.CargoCursoResquestDTO;
import com.projeto.cefom.dto.request.CursoRequestDTO;
import com.projeto.cefom.dto.response.CursoListarResponseDTO;
import com.projeto.cefom.dto.response.CursoResponseDTO;
import com.projeto.cefom.mapper.CursoMapper;
import com.projeto.cefom.model.Curso;
import com.projeto.cefom.repository.CursoRepository;
import com.projeto.cefom.utils.TextoUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CursoService {
    private final CursoRepository cursoRepository;
    private final VinculoCursoCargoService vinculoCursoCargoService;
    private final CursoMapper cursoMapper;

    public CursoService(CursoRepository cursoRepository, VinculoCursoCargoService vinculoCursoCargoService, CursoMapper cursoMapper) {
        this.cursoRepository = cursoRepository;
        this.vinculoCursoCargoService = vinculoCursoCargoService;
        this.cursoMapper = cursoMapper;
    }

    @Transactional
    public CursoResponseDTO criar(CursoRequestDTO dto) {

        if (cursoRepository.existsByProtocoloAprovacao(TextoUtils.manterSomenteNumeros(dto.protocoloAprovacao()))) {
            throw new RegraNegocioException("Curso já cadastrado.");
        }

        Curso curso = criarCurso(dto);

        Curso cursoSalvo = salvar(curso);

        return cursoMapper.toResponseDTO(cursoSalvo);
    }

    @Transactional
    public CursoResponseDTO atualizar(Integer idCurso, CursoRequestDTO dto) {

        if (cursoRepository.existsByProtocoloAprovacaoAndIdCursoNot(TextoUtils.manterSomenteNumeros(dto.protocoloAprovacao()), idCurso)) {
            throw new RegraNegocioException("Curso já cadastrado.");
        }

        Curso curso = buscarCurso(idCurso);

        atualizarCurso(dto, curso);

        Curso cursoSalvo = salvar(curso);

        return cursoMapper.toResponseDTO(cursoSalvo);
    }

    @Transactional(readOnly = true)
    public List<CursoListarResponseDTO> listar() {
        return cursoRepository.findAll()
                .stream()
                .map(cursoMapper::toListarResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<CursoListarResponseDTO> listar(Pageable pageable, String nome) {
        if (nome == null || nome.isBlank()) {
            return cursoRepository.findAll(pageable)
                    .map(cursoMapper::toListarResponseDTO);
        }
        return cursoRepository.findByNomeCursoContainingIgnoreCase(TextoUtils.normalizar(nome), pageable)
                .map(cursoMapper::toListarResponseDTO);
    }

    @Transactional(readOnly = true)
    public CursoResponseDTO buscarPorId(Integer idCurso) {
        Curso curso = buscarCurso(idCurso);
        return cursoMapper.toResponseDTO(curso);
    }

    @Transactional
    public void excluirPorId(Integer idCurso) {
        Curso curso = buscarCurso(idCurso);
        cursoRepository.deleteById(idCurso);
    }

    @Transactional(readOnly = true)
    public Curso buscarCurso(Integer idCurso) {
        Curso curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Curso com ID "+idCurso+" não encontrado."));
        return curso;
    }

    public Curso salvar(Curso curso) {
        Curso salvo = cursoRepository.save(curso);
        return salvo;
    }

    public Curso criarCurso(CursoRequestDTO dto){
        Curso curso = new Curso();
        curso.setNomeCurso(dto.nomeCurso());
        curso.setNomePrograma(dto.nomePrograma());
        curso.setModalidadeCurso(dto.modalidadeCurso());
        curso.setProtocoloAprovacao(dto.protocoloAprovacao());
        curso.setCargaHorariaBasica(dto.cargaHorariaTeoricaBasica());
        curso.setCargaHorariaEspecifica(dto.cargaHorariaTeoricaEspecifica());
        curso.setCargaHorariaTeoricaInicial(dto.cargaHorariaTeoricaInicial());
        curso.setCargaHorariaPratica(dto.cargaHorariaPratica());
        curso.setDataInicio(dto.dataInicio());
        curso.setDataFim(dto.dataFim());

        curso = salvar(curso);

        //Lista de cargos
        for (CargoCursoResquestDTO cargoDto : dto.cargos()) {
            vinculoCursoCargoService.adicionarCursoCargo(curso, cargoDto.idCargo());
        }

        return curso;
    }

    public void atualizarCurso(CursoRequestDTO dto, Curso curso) {
        curso.setNomeCurso(dto.nomeCurso());
        curso.setNomePrograma(dto.nomePrograma());
        curso.setModalidadeCurso(dto.modalidadeCurso());
        curso.setProtocoloAprovacao(dto.protocoloAprovacao());
        curso.setCargaHorariaBasica(dto.cargaHorariaTeoricaBasica());
        curso.setCargaHorariaEspecifica(dto.cargaHorariaTeoricaEspecifica());
        curso.setCargaHorariaTeoricaInicial(dto.cargaHorariaTeoricaInicial());
        curso.setCargaHorariaPratica(dto.cargaHorariaPratica());

        // --- Atualizar vínculos ---

        Set<Integer> cargosAtuais = curso.getCargos()
                .stream()
                .map(v -> v.getCargo().getIdCargo())
                .collect(Collectors.toSet());

        Set<Integer> cargosNovos = dto.cargos()
                .stream()
                .map(CargoCursoResquestDTO::idCargo)
                .collect(Collectors.toSet());

        // Adicionar novos
        Set<Integer> paraAdicionar = new HashSet<>(cargosNovos);
        paraAdicionar.removeAll(cargosAtuais);

        for (Integer idCargo : paraAdicionar) {
            vinculoCursoCargoService.adicionarCursoCargo(curso, idCargo);
        }

        // Remover os que saíram
        Set<Integer> paraRemover = new HashSet<>(cargosAtuais);
        paraRemover.removeAll(cargosNovos);

        for (Integer idCargo : paraRemover) {
            vinculoCursoCargoService.removerCursoCargo(curso, idCargo);
        }
    }
}
