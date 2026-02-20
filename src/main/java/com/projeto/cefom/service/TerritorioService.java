package com.projeto.cefom.service;

import com.projeto.cefom.dto.request.TerritorioBairroRequestDTO;
import com.projeto.cefom.dto.response.TerritorioListarResponseDTO;
import com.projeto.cefom.dto.request.TerritorioRequestDTO;
import com.projeto.cefom.dto.response.TerritorioResponseDTO;
import com.projeto.cefom.exceptions.RecursoNaoEncontradoException;
import com.projeto.cefom.exceptions.RegraNegocioException;
import com.projeto.cefom.mapper.TerritorioMapper;
import com.projeto.cefom.model.Territorio;
import com.projeto.cefom.repository.TerritorioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class TerritorioService {

    private final TerritorioRepository territorioRepository;
    private final TerritorioMapper territorioMapper;
    private final TerritorioAplicacaoService territorioAplicacaoService;

    public TerritorioService(TerritorioRepository territorioRepository, TerritorioMapper territorioMapper, TerritorioAplicacaoService territorioAplicacaoService) {
        this.territorioRepository = territorioRepository;
        this.territorioMapper = territorioMapper;
        this.territorioAplicacaoService = territorioAplicacaoService;
    }

    @Transactional
    public TerritorioResponseDTO criar(TerritorioRequestDTO dto) {

        if (territorioRepository.existsByResultado(dto.territorio())) {
            throw new RegraNegocioException("Território já cadastrado.");
        }

        Territorio territorioSalvo = criarTerritorio(dto.territorio());
        return territorioMapper.toResponseDTO(territorioSalvo);
    }

    @Transactional
    public TerritorioResponseDTO atualizar(Integer idTerritorio, TerritorioRequestDTO dto) {

        if (territorioRepository.existsByResultadoAndIdTerritorioNot(dto.territorio(), idTerritorio)) {
            throw new RegraNegocioException("Território já cadastrado.");
        }

        Territorio territorio = buscarTerritorio(idTerritorio);
        territorio.setResultado(dto.territorio());

        Territorio territorioSalvo = salvar(territorio);
        return territorioMapper.toResponseDTO(territorioSalvo);
    }


    @Transactional
    public TerritorioResponseDTO atualizarBairros(Integer idTerritorio, TerritorioBairroRequestDTO dto) {
        Territorio territorio = buscarTerritorio(idTerritorio);

        List<String> bairrosDesejados = dto.bairros();
        if (bairrosDesejados == null) bairrosDesejados = List.of();

        Set<String> desejados = bairrosDesejados.stream()
                .filter(s -> s != null && !s.isBlank())
                .map(String::trim)
                .collect(java.util.stream.Collectors.toSet());

        Set<String> atuais = new java.util.HashSet<>(territorio.getBairros());

        //Remove os bairros
        atuais.stream()
                .filter(b -> !desejados.contains(b))
                .forEach(b -> {
                    territorio.removerBairro(b);
                    territorioAplicacaoService.removerTerritorioEnderecos(b);
                });

        //Valida os bairros
        desejados.stream()
                .filter(b -> !atuais.contains(b))
                .forEach(bairro -> {
                    territorioRepository.findByBairro(bairro)
                            .ifPresent(t -> {
                                throw new RegraNegocioException("O bairro "+bairro+" já está cadastrado no territirótio "+t.getResultado()+".");
                            });
                });

        //Adiciona os bairros
        desejados.stream()
                .filter(b -> !atuais.contains(b))
                .forEach(b -> {
                    territorio.adicionarBairro(b);
                    territorioAplicacaoService.adicionarTerritorioEnderecos(b,territorio);
                });

        Territorio territorioSalvo = territorioRepository.save(territorio);
        return territorioMapper.toResponseDTO(territorioSalvo);
    }

    @Transactional(readOnly = true)
    public TerritorioResponseDTO buscarPorId(Integer idTerritorio) {
        Territorio territorio = buscarTerritorio(idTerritorio);
        return territorioMapper.toResponseDTO(territorio);
    }

    @Transactional(readOnly = true)
    public List<TerritorioListarResponseDTO> listar() {
        return territorioRepository.findAll()
                .stream()
                .map(territorioMapper::toListarResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<TerritorioListarResponseDTO> listar(Pageable pageable, String nome) {
        if (nome == null || nome.isBlank()) {
            return territorioRepository.findAll(pageable)
                    .map(territorioMapper::toListarResponseDTO);
        }
        return territorioRepository.findByResultadoContainingIgnoreCase(nome, pageable)
                .map(territorioMapper::toListarResponseDTO);
    }


    @Transactional
    public void excluirPorId(Integer idTerritorio) {
        Territorio territorio = buscarTerritorio(idTerritorio);
        territorioRepository.deleteById(idTerritorio);
    }

    @Transactional(readOnly = true)
    public Territorio buscarPorBairro(String bairro) {
        if (bairro == null || bairro.isBlank()) {
            throw new RegraNegocioException("Bairro não pode ser vazio.");
        }

        String bairroNormalizado = bairro.trim().toUpperCase();

        return territorioRepository.findByBairro(bairroNormalizado)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Território com Bairro "+bairro+" não encontrado."));

    }

    @Transactional
    public Territorio buscarOuCriarPorResultado(String resultado) {
        if (resultado == null) {
            throw new RegraNegocioException("Resultado não pode ser vazio.");
        }

        return territorioRepository.findByResultado(resultado)
                .orElseGet(() -> criarTerritorio(resultado));
    }

    private Territorio criarTerritorio(String resultado) {
        Territorio territorio = new Territorio();
        territorio.setResultado(resultado);
        Territorio territorioSalvo = salvar(territorio);
        return territorioSalvo;
    }

    private Territorio salvar(Territorio territorio) {
        Territorio salvo = territorioRepository.save(territorio);
        return salvo;
    }

    public Territorio buscarTerritorio(Integer idTerritorio) {
        Territorio territorio = territorioRepository.findById(idTerritorio)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Território com ID "+idTerritorio+" não encontrado."));
        return territorio;
    }

}
