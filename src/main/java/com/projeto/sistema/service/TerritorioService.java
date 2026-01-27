package com.projeto.sistema.service;

import com.projeto.sistema.dto.request.TerritorioBairroRequestDTO;
import com.projeto.sistema.dto.response.TerritorioListarResponseDTO;
import com.projeto.sistema.dto.request.TerritorioRequestDTO;
import com.projeto.sistema.dto.response.TerritorioResponseDTO;
import com.projeto.sistema.exceptions.RecursoNaoEncontradoException;
import com.projeto.sistema.exceptions.RegraNegocioException;
import com.projeto.sistema.mapper.TerritorioMapper;
import com.projeto.sistema.model.Territorio;
import com.projeto.sistema.repository.TerritorioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class TerritorioService {

    private final TerritorioRepository territorioRepository;
    private final TerritorioMapper territorioMapper;

    public TerritorioService(TerritorioRepository territorioRepository, TerritorioMapper territorioMapper) {
        this.territorioRepository = territorioRepository;
        this.territorioMapper = territorioMapper;
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
                .forEach(territorio::removerBairro);

        //Adiciona os bairros
        desejados.stream()
                .filter(b -> !atuais.contains(b))
                .forEach(territorio::adicionarBairro);

        Territorio territorioSalvo = territorioRepository.save(territorio);
        return territorioMapper.toResponseDTO(territorioSalvo);
    }

    /* //Adicionando Bairros, usando duas listas uma para adicionar e outra para remover
    @Transactional
    public TerritorioResponseDTO atualizarBairros(Integer idTerritorio, TerritorioBairroRequestDTO dto) {
        Territorio territorio = buscarTerritorio(idTerritorio);

        //Adiciona os bairros
        if(dto.adicionar() != null && !dto.adicionar().isEmpty()) {
            dto.adicionar().forEach(territorio::adicionarBairro);
        }

        //Remove os bairros
        if(dto.remover() != null && !dto.remover().isEmpty()) {
            dto.remover().forEach(territorio::removerBairro);
        }

        Territorio territorioSalvo = territorioRepository.save(territorio);
        return territorioMapper.toResponseDTO(territorioSalvo);

    }

    */

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
