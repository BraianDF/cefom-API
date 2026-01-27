package com.projeto.sistema.controller;

import com.projeto.sistema.dto.request.EscolaRequestDTO;
import com.projeto.sistema.dto.response.EscolaResponseDTO;
import com.projeto.sistema.dto.response.EscolaListarResponseDTO;
import com.projeto.sistema.service.EscolaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/escolas")
public class EscolaController {
    private final EscolaService escolaService;

    public EscolaController(EscolaService escolaService) {
        this.escolaService = escolaService;
    }

    // OK
    @PostMapping
    public ResponseEntity<EscolaResponseDTO> criar(@Valid @RequestBody EscolaRequestDTO dto) {
        EscolaResponseDTO response = escolaService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // OK
    @PutMapping("/{idEscola}")
    public ResponseEntity<EscolaResponseDTO> atualizar(@PathVariable Integer idEscola, @Valid @RequestBody EscolaRequestDTO dto) {
        EscolaResponseDTO response = escolaService.atualizar(idEscola, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // OK
    @GetMapping
    public ResponseEntity<List<EscolaListarResponseDTO>> listar() {
        List<EscolaListarResponseDTO> response = escolaService.listar();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // OK
    @GetMapping("/{idEscola}")
    public ResponseEntity<EscolaResponseDTO> buscarPorId(@PathVariable Integer idEscola) {
        EscolaResponseDTO response = escolaService.buscarPorId(idEscola);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // OK
    @DeleteMapping("/{idEscola}")
    public ResponseEntity<Void> excluirPorId(@PathVariable Integer idEscola) {
        escolaService.excluirPorId(idEscola);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
