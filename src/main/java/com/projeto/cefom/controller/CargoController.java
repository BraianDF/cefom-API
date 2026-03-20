package com.projeto.cefom.controller;

import com.projeto.cefom.dto.request.CargoRequestDTO;
import com.projeto.cefom.dto.response.CargoListarResponseDTO;
import com.projeto.cefom.dto.response.CargoResponseDTO;
import com.projeto.cefom.dto.response.CargoSelectResponseDTO;
import com.projeto.cefom.service.CargoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cargos")
public class CargoController {

    private final CargoService cargoService;

    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @PostMapping
    public ResponseEntity<CargoResponseDTO> criar(@Valid @RequestBody CargoRequestDTO dto) {
        CargoResponseDTO response = cargoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{idCargo}")
    public ResponseEntity<CargoResponseDTO> atualizar(@PathVariable Integer idCargo,@Valid  @RequestBody CargoRequestDTO dto) {
        CargoResponseDTO response = cargoService.atualizar(idCargo, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<CargoListarResponseDTO>> listar(@PageableDefault(page = 0, size = 10, sort = "funcao", direction = Sort.Direction.ASC) Pageable pageable, @RequestParam(defaultValue = "") String nome) {
        Page<CargoListarResponseDTO> response = cargoService.listar(pageable, nome);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/select")
    public ResponseEntity<List<CargoSelectResponseDTO>> listarSelect() {
        List<CargoSelectResponseDTO> response = cargoService.listarSelect();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idCargo}")
    public ResponseEntity<CargoResponseDTO> buscarPorId(@PathVariable Integer idCargo) {
        CargoResponseDTO response = cargoService.buscarPorId(idCargo);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{idCargo}")
    public  ResponseEntity<Void> excluirPorId(@PathVariable Integer idCargo) {
        cargoService.excluirPorId(idCargo);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
