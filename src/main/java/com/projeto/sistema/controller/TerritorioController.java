package com.projeto.sistema.controller;

import com.projeto.sistema.dto.request.TerritorioBairroRequestDTO;
import com.projeto.sistema.dto.request.TerritorioRequestDTO;
import com.projeto.sistema.dto.response.TerritorioListarResponseDTO;
import com.projeto.sistema.dto.response.TerritorioResponseDTO;
import com.projeto.sistema.service.TerritorioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/territorios")
public class TerritorioController {
    private final TerritorioService territorioService;

    public TerritorioController(TerritorioService territorioService) {
        this.territorioService = territorioService;
    }

    @PostMapping
    public ResponseEntity<TerritorioResponseDTO> criar(@Valid @RequestBody TerritorioRequestDTO dto) {
        TerritorioResponseDTO response = territorioService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{idTerritorio}")
    public ResponseEntity<TerritorioResponseDTO> atualizar(@PathVariable Integer idTerritorio, @Valid @RequestBody TerritorioRequestDTO dto) {
        TerritorioResponseDTO response = territorioService.atualizar(idTerritorio, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{idTerritorio}/bairros")
    public ResponseEntity<TerritorioResponseDTO> atualizarBairros(@PathVariable Integer idTerritorio, @Valid @RequestBody TerritorioBairroRequestDTO dto) {
        TerritorioResponseDTO response = territorioService.atualizarBairros(idTerritorio, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TerritorioListarResponseDTO>> listar() {
        List<TerritorioListarResponseDTO> response = territorioService.listar();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idTerritorio}")
    public ResponseEntity<TerritorioResponseDTO> buscarPorId(@PathVariable Integer idTerritorio) {
        TerritorioResponseDTO response = territorioService.buscarPorId(idTerritorio);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{idTerritorio}")
    public ResponseEntity<Void> excluir(@PathVariable Integer idTerritorio) {
        territorioService.excluirPorId(idTerritorio);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
