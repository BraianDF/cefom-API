package com.projeto.sistema.controller;

import com.projeto.sistema.dto.request.CaracteristicaAtualizarRequestDTO;
import com.projeto.sistema.dto.response.CaracteristicaListarResponseDTO;
import com.projeto.sistema.dto.response.CaracteristicaResponseDTO;
import com.projeto.sistema.service.CaracteristicaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/adolescentes")
public class CaracteristicaController {

    private final CaracteristicaService caracteristicaService;

    public CaracteristicaController(CaracteristicaService caracteristicaService) {
        this.caracteristicaService = caracteristicaService;
    }

    @PutMapping("/{idAdolescente}/caracteristicas")
    public ResponseEntity<CaracteristicaResponseDTO> atualizar(@PathVariable Integer idAdolescente, @Valid @RequestBody CaracteristicaAtualizarRequestDTO dto) {
        CaracteristicaResponseDTO response = caracteristicaService.atualizar(idAdolescente, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idAdolescente}/caracteristicas")
    public ResponseEntity<List<CaracteristicaListarResponseDTO>> listar(@PathVariable Integer idAdolescente) {
        List<CaracteristicaListarResponseDTO> response = caracteristicaService.listar(idAdolescente);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idAdolescente}/caracteristicas/{idCaracteristica}")
    public ResponseEntity<CaracteristicaResponseDTO> buscarPorId(@PathVariable Integer idAdolescente, @PathVariable Integer idCaracteristica) {
        CaracteristicaResponseDTO response = caracteristicaService.buscarPorId(idAdolescente, idCaracteristica);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{idAdolescente}/caracteristicas/{idCaracteristica}")
    public ResponseEntity<Void> excluirPorId(@PathVariable Integer idAdolescente, @PathVariable Integer idCaracteristica) {
        caracteristicaService.excluirPorId(idAdolescente, idCaracteristica);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
