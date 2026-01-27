package com.projeto.sistema.service;

import com.projeto.sistema.dto.request.EscolaridadeAtualizarRequestDTO;
import com.projeto.sistema.dto.request.EscolaridadeRequestDTO;
import com.projeto.sistema.dto.response.EscolaridadeListarResponseDTO;
import com.projeto.sistema.dto.response.EscolaridadeResponseDTO;
import com.projeto.sistema.exceptions.RecursoNaoEncontradoException;
import com.projeto.sistema.exceptions.RegraNegocioException;
import com.projeto.sistema.mapper.EscolaridadeMapper;
import com.projeto.sistema.model.Adolescente;
import com.projeto.sistema.model.Endereco;
import com.projeto.sistema.model.Escola;
import com.projeto.sistema.model.Escolaridade;
import com.projeto.sistema.repository.EscolaridadeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class EscolaridadeService {

    private final EscolaridadeRepository escolaridadeRepository;
    private final EscolaService escolaService;
    private final AdolescenteService adolescenteService;
    private final EscolaridadeMapper escolaridadeMapper;

    public EscolaridadeService(EscolaridadeRepository escolaridadeRepository, EscolaService escolaService, AdolescenteService adolescenteService, EscolaridadeMapper escolaridadeMapper) {
        this.escolaridadeRepository = escolaridadeRepository;
        this.escolaService = escolaService;
        this.adolescenteService = adolescenteService;
        this.escolaridadeMapper = escolaridadeMapper;
    }

    @Transactional
    public EscolaridadeResponseDTO atualizar(Integer idAdolescente, EscolaridadeAtualizarRequestDTO dto) {
        LocalDate dataModificacao = dto.dataModificacao();
        if(dataModificacao == null) {
            dataModificacao = LocalDate.now();
        }

        //Verifica se o adolescente existe e retorna ele
        Adolescente adolescente = adolescenteService.buscarAdolescente(idAdolescente);

        //Atualiza
        atualizarEscolaridade(dto.escolaridade(), adolescente, dataModificacao);

        //Salva o adolescente no banco
        Adolescente adolescenteSalvo = adolescenteService.salvar(adolescente);

        //Retorna o ResponseDTO
        return escolaridadeMapper.toResponseDTO(adolescenteSalvo, dataModificacao);
    }

    @Transactional(readOnly = true)
    public List<EscolaridadeListarResponseDTO> listar(Integer idAdolescente) {
        //Verifica se o adolescente existe e retorna ele
        Adolescente adolescente = adolescenteService.buscarAdolescente(idAdolescente);

        //Retorna todos que o adolescente tiver
        return escolaridadeRepository.findByAdolescenteIdAdolescenteOrderByDataInicioDesc(idAdolescente)
                .stream()
                .map(escolaridadeMapper::toListarResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public EscolaridadeResponseDTO buscarPorId(Integer idAdolescente, Integer idEscolaridade) {
        Escolaridade escolaridade = buscarEscolaridadeAdolescente(idAdolescente, idEscolaridade);
        return escolaridadeMapper.toResponseDTO(escolaridade);
    }

    @Transactional
    public void excluirPorId(Integer idAdolescente, Integer idEscolaridade) {
        Escolaridade escolaridade = buscarEscolaridadeAdolescente(idAdolescente, idEscolaridade);
        escolaridadeRepository.deleteById(idEscolaridade);
    }

    public void atualizarEscolaridade(EscolaridadeRequestDTO dto, Adolescente adolescente, LocalDate data) {

        Escola escola = escolaService.buscarEscola(dto.idEscola());

        Escolaridade escolaridadeAtual = adolescente.getEscolaridades().stream()
                .filter(e -> e.getDataFim() == null)
                .max(Comparator.comparing(Escolaridade::getDataInicio))
                .orElse(null);

        if (escolaridadeAtual != null && escolaridadeIgual(dto, escolaridadeAtual)) {
            // Escolaridade não mudou → não faz nada
            return;
        }

        // Encerra escolaridade atual
        if (escolaridadeAtual != null) {
            escolaridadeAtual.setDataFim(data);
        }

        // Cria nova escolaridade
        criarEscolaridade(dto, adolescente, data);
    }

    public boolean escolaridadeIgual(EscolaridadeRequestDTO dto, Escolaridade e) {
        return Objects.equals(e.getCurso(), dto.curso()) &&
                Objects.equals(e.getSerie(), dto.serie()) &&
                Objects.equals(e.getPeriodo(), dto.periodo()) &&
                Objects.equals(e.getRaEscolar(), dto.raEscolar()) &&
                Objects.equals(e.getEscola().getIdEscola(), dto.idEscola());
    }

    public void criarEscolaridade(EscolaridadeRequestDTO dto, Adolescente adolescente, LocalDate data) {
        Escola escola = escolaService.buscarEscola(dto.idEscola());

        Escolaridade escolaridade = new Escolaridade();
        escolaridade.setCurso(dto.curso());
        escolaridade.setSerie(dto.serie());
        escolaridade.setPeriodo(dto.periodo());
        escolaridade.setRaEscolar(dto.raEscolar());
        escolaridade.setDataInicio(data);

        escola.adicionarEscolaridade(escolaridade);
        adolescente.adicionarEscolaridade(escolaridade);
    }

    private Escolaridade buscarEscolaridadeAdolescente(Integer idAdolescente, Integer idEscolaridade) {
        Adolescente adolescente = adolescenteService.buscarAdolescente(idAdolescente);

        Escolaridade escolaridade = escolaridadeRepository.findById(idEscolaridade)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Escolaridade com ID "+idEscolaridade+" não encontrada."));

        if (escolaridade.getAdolescente() == null) {
            throw new RegraNegocioException("Escolaridade não pertence a um adolescente.");
        }

        if (!escolaridade.getAdolescente().getIdAdolescente().equals(idAdolescente)) {
            throw new RegraNegocioException("Escolaridade não pertence ao adolescente.");
        }

        return escolaridade;
    }

}
