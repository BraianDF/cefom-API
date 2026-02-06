package com.projeto.cefom.controller;

import com.projeto.cefom.dto.request.CaracteristicaAtualizarRequestDTO;
import com.projeto.cefom.dto.response.CaracteristicaListarResponseDTO;
import com.projeto.cefom.dto.response.CaracteristicaResponseDTO;
import com.projeto.cefom.service.CaracteristicaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<CaracteristicaListarResponseDTO>> listar(@PathVariable Integer idAdolescente, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<CaracteristicaListarResponseDTO> response = caracteristicaService.listar(idAdolescente, pageable);
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
