package com.projeto.cefom.controller;

import com.projeto.cefom.dto.request.TerritorioBairroRequestDTO;
import com.projeto.cefom.dto.request.TerritorioRequestDTO;
import com.projeto.cefom.dto.response.TerritorioListarResponseDTO;
import com.projeto.cefom.dto.response.TerritorioPendenciasResponseDTO;
import com.projeto.cefom.dto.response.TerritorioResponseDTO;
import com.projeto.cefom.service.TerritorioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<TerritorioListarResponseDTO>> listar(@PageableDefault(page = 0, size = 10) Pageable pageable, @RequestParam(defaultValue = "") String nome) {
        Page<TerritorioListarResponseDTO> response = territorioService.listar(pageable, nome);
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

    @GetMapping("/pendencias")
    public ResponseEntity<TerritorioPendenciasResponseDTO> enderecosSemTerritorio() {
        TerritorioPendenciasResponseDTO response = territorioService.enderecosSemTerritorio();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
