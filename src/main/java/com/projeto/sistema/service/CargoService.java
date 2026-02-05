package com.projeto.sistema.service;

import com.projeto.sistema.dto.response.CargoSelectResponseDTO;
import com.projeto.sistema.exceptions.RecursoNaoEncontradoException;
import com.projeto.sistema.exceptions.RegraNegocioException;
import com.projeto.sistema.dto.request.CargoRequestDTO;
import com.projeto.sistema.dto.response.CargoListarResponseDTO;
import com.projeto.sistema.dto.response.CargoResponseDTO;
import com.projeto.sistema.mapper.CargoMapper;
import com.projeto.sistema.model.Cargo;
import com.projeto.sistema.repository.CargoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CargoService {
    CargoRepository cargoRepository;
    CargoMapper cargoMapper;

    public CargoService(CargoRepository cargoRepository, CargoMapper cargoMapper) {
        this.cargoRepository = cargoRepository;
        this.cargoMapper = cargoMapper;
    }

    @Transactional
    public CargoResponseDTO criar(CargoRequestDTO dto) {

        if (cargoRepository.existsByFuncao(dto.funcao())) {
            throw new RegraNegocioException("Cargo já cadastrado.");
        }

        Cargo cargo = criarCargo(dto);

        Cargo cargoSalvo = salvar(cargo);

        return cargoMapper.toResponseDTO(cargoSalvo);
    }

    @Transactional
    public CargoResponseDTO atualizar(Integer idCargo, CargoRequestDTO dto) {

        if (cargoRepository.existsByFuncaoAndIdCargoNot(dto.funcao(), idCargo)) {
            throw new RegraNegocioException("Cargo já cadastrado.");
        }

        Cargo cargo = buscarCargo(idCargo);

        atualizarCargo(dto, cargo);

        Cargo cargoSalvo = salvar(cargo);

        return cargoMapper.toResponseDTO(cargoSalvo);
    }

    @Transactional(readOnly = true)
    public List<CargoSelectResponseDTO> listarSelect() {
        return cargoRepository.findAll()
                .stream()
                .map(cargoMapper::toSelectResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<CargoListarResponseDTO> listar(Pageable pageable, String nome) {
        if (nome == null || nome.isBlank()) {
            return cargoRepository.findAll(pageable)
                    .map(cargoMapper::toListarResponseDTO);
        }
        return cargoRepository.findByFuncaoContainingIgnoreCase(nome, pageable)
                .map(cargoMapper::toListarResponseDTO);
    }

    @Transactional(readOnly = true)
    public CargoResponseDTO buscarPorId(Integer idCargo) {
        Cargo cargo = buscarCargo(idCargo);
        return cargoMapper.toResponseDTO(cargo);
    }

    @Transactional
    public void excluirPorId(Integer idCargo) {
        Cargo cargo = buscarCargo(idCargo);
        cargoRepository.deleteById(idCargo);
    }

    @Transactional(readOnly = true)
    public Cargo buscarCargo(Integer idCargo) {
        Cargo cargo = cargoRepository.findById(idCargo)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cargo com ID "+idCargo+" não encontrado."));
        return cargo;
    }

    public Cargo salvar(Cargo cargo) {
        Cargo salvo = cargoRepository.save(cargo);
        return salvo;
    }

    public Cargo criarCargo(CargoRequestDTO dto){
        Cargo cargo = new Cargo();
        cargo.setFuncao(dto.funcao());
        cargo.setCbo(dto.cbo());
        return cargo;
    }

    public void atualizarCargo(CargoRequestDTO dto, Cargo cargo) {
        cargo.setCbo(dto.cbo());
        cargo.setFuncao(dto.funcao());
    }
}
