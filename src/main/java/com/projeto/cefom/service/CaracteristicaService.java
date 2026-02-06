package com.projeto.cefom.service;


import com.projeto.cefom.dto.request.CaracteristicaAtualizarRequestDTO;
import com.projeto.cefom.dto.request.CaracteristicaRequestDTO;
import com.projeto.cefom.dto.response.CaracteristicaListarResponseDTO;
import com.projeto.cefom.dto.response.CaracteristicaResponseDTO;
import com.projeto.cefom.exceptions.RecursoNaoEncontradoException;
import com.projeto.cefom.exceptions.RegraNegocioException;
import com.projeto.cefom.mapper.CaracteristicaMapper;
import com.projeto.cefom.model.Adolescente;
import com.projeto.cefom.model.Caracteristica;
import com.projeto.cefom.repository.CaracteristicaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

@Service
public class CaracteristicaService {

    private final CaracteristicaRepository caracteristicaRepository;
    private final AdolescenteService adolescenteService;
    private final CaracteristicaMapper caracteristicaMapper;

    public CaracteristicaService(CaracteristicaRepository caracteristicaRepository, AdolescenteService adolescenteService, CaracteristicaMapper caracteristicaMapper) {
        this.caracteristicaRepository = caracteristicaRepository;
        this.adolescenteService = adolescenteService;
        this.caracteristicaMapper = caracteristicaMapper;
    }

    @Transactional
    public CaracteristicaResponseDTO atualizar(Integer idAdolescente, CaracteristicaAtualizarRequestDTO dto) {
        LocalDate dataModificacao = dto.dataModificacao();
        if(dataModificacao == null) {
            dataModificacao = LocalDate.now();
        }

        //Verifica se o adolescente existe e retorna ele
        Adolescente adolescente = adolescenteService.buscarAdolescente(idAdolescente);

        //Atualiza
        atualizarCaracteristica(dto.caracteristica(), adolescente, dataModificacao);

        //Salva o adolescente no banco
        Adolescente adolescenteSalvo = adolescenteService.salvar(adolescente);

        //Retorna o ResponseDTO
        return caracteristicaMapper.toResponseDTO(adolescenteSalvo, dataModificacao);
    }

    @Transactional(readOnly = true)
    public Page<CaracteristicaListarResponseDTO> listar(Integer idAdolescente, Pageable pageable) {
        //Verifica se o adolescente existe e retorna ele
        Adolescente adolescente = adolescenteService.buscarAdolescente(idAdolescente);

        //Retorna todos que o adolescente tiver
        return caracteristicaRepository.findByAdolescenteIdAdolescenteOrderByDataInicioDesc(idAdolescente, pageable)
                .map(caracteristicaMapper::toListarResponseDTO);
    }

    @Transactional(readOnly = true)
    public CaracteristicaResponseDTO buscarPorId(Integer idAdolescente, Integer idCaracteristica) {
        Caracteristica caracteristica = buscarCaracteristicaAdolescente(idAdolescente, idCaracteristica);
        return caracteristicaMapper.toResponseDTO(caracteristica);
    }

    @Transactional
    public void excluirPorId(Integer idAdolescente, Integer idCaracteristica) {
        Caracteristica caracteristica = buscarCaracteristicaAdolescente(idAdolescente, idCaracteristica);
        caracteristicaRepository.deleteById(idCaracteristica);
    }

    public void atualizarCaracteristica(CaracteristicaRequestDTO dto, Adolescente adolescente, LocalDate data) {
        Caracteristica caracteristicaAtual = adolescente.getCaracteristicas().stream()
                .filter(c -> c.getDataFim() == null)
                .max(Comparator.comparing(Caracteristica::getDataInicio))
                .orElse(null);

        if (caracteristicaAtual != null && caracteristicaIgual(dto, caracteristicaAtual)) {
            return;
        }

        if (caracteristicaAtual != null) {
            caracteristicaAtual.setDataFim(data);
        }

        criarCaracteristica(dto, adolescente, data);
    }

    public void criarCaracteristica(CaracteristicaRequestDTO dto, Adolescente adolescente, LocalDate data) {
        Caracteristica c = new Caracteristica();
        if (dto.corOlhos() != null) {
            c.setCorOlhos(dto.corOlhos());
        }
        if (dto.corCabelo() != null) {
            c.setCorCabelo(dto.corCabelo());
        }
        if (dto.corPele() != null) {
            c.setCorPele(dto.corPele());
        }
        if (dto.altura() != null) {
            c.setAltura(dto.altura());
        }
        if (dto.peso() != null) {
            c.setPeso(dto.peso());
        }
        if (dto.habilidade() != null) {
            c.setHabilidade(dto.habilidade());
        }
        c.setDataInicio(data);

        adolescente.adicionarCaracteristica(c);
    }

    public boolean caracteristicaIgual(CaracteristicaRequestDTO dto, Caracteristica c) {

        return Objects.equals(c.getCorOlhos(), dto.corOlhos())
                && Objects.equals(c.getCorCabelo(), dto.corCabelo())
                && Objects.equals(c.getCorPele(), dto.corPele())
                && Objects.equals(c.getAltura(), dto.altura())
                && Objects.equals(c.getPeso(), dto.peso())
                && Objects.equals(c.getHabilidade(), dto.habilidade());
    }

    private Caracteristica buscarCaracteristicaAdolescente(Integer idAdolescente, Integer idCaracteristica) {
        Adolescente adolescente = adolescenteService.buscarAdolescente(idAdolescente);

        Caracteristica caracteristica = caracteristicaRepository.findById(idCaracteristica)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Caracteristica com ID "+idCaracteristica+" não encontrada."));

        if (caracteristica.getAdolescente() == null) {
            throw new RegraNegocioException("Caracteristica não pertence a um adolescente.");
        }

        if (!caracteristica.getAdolescente().getIdAdolescente().equals(idAdolescente)) {
            throw new RegraNegocioException("Caracteristica não pertence ao adolescente.");
        }

        return caracteristica;
    }
}
