package com.projeto.sistema.controller;


import com.projeto.sistema.dto.request.EntrevistaRequestDTO;
import com.projeto.sistema.dto.request.VinculoEntrevistaMatriculaRequestDTO;
import com.projeto.sistema.dto.response.EmpresaListarEntrevistaResponseDTO;
import com.projeto.sistema.dto.response.EntrevistaListarResponseDTO;
import com.projeto.sistema.dto.response.EntrevistaResponseDTO;
import com.projeto.sistema.service.EntrevistaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entrevistas")
public class EntrevistaController {
    private final EntrevistaService entrevistaService;

    public EntrevistaController(EntrevistaService entrevistaService) {
        this.entrevistaService = entrevistaService;
    }

    @PostMapping
    public ResponseEntity<EntrevistaResponseDTO> criar(@Valid @RequestBody EntrevistaRequestDTO dto) {
        EntrevistaResponseDTO response = entrevistaService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{idEntrevista}")
    public ResponseEntity<EntrevistaResponseDTO> atualizar(@PathVariable Integer idEntrevista, @Valid @RequestBody EntrevistaRequestDTO dto) {
        EntrevistaResponseDTO response = entrevistaService.atualizar(idEntrevista, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{idEntrevista}")
    public ResponseEntity<EntrevistaResponseDTO> cancelar(@PathVariable Integer idEntrevista) {
        EntrevistaResponseDTO response = entrevistaService.cancelar(idEntrevista);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<List<EntrevistaListarResponseDTO>> listar() {
        List<EntrevistaListarResponseDTO> response = entrevistaService.listar();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idEntrevista}")
    public ResponseEntity<EntrevistaResponseDTO> buscarPorId(@PathVariable Integer idEntrevista) {
        EntrevistaResponseDTO response = entrevistaService.buscarPorId(idEntrevista);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{idEntrevista}")
    public  ResponseEntity<Void> excluirPorId(@PathVariable Integer idEntrevista) {
        entrevistaService.excluirPorId(idEntrevista);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{idEntrevista}/adolescentes")
    public ResponseEntity<EntrevistaResponseDTO> adicionarAdolescente(@PathVariable Integer idEntrevista, @Valid @RequestBody VinculoEntrevistaMatriculaRequestDTO dto) {
        EntrevistaResponseDTO response = entrevistaService.adicionarAdolescente(idEntrevista, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{idEntrevista}/adolescentes")
    public ResponseEntity<EntrevistaResponseDTO> atualizarAdolescente(@PathVariable Integer idEntrevista, @Valid @RequestBody VinculoEntrevistaMatriculaRequestDTO dto) {
        EntrevistaResponseDTO response = entrevistaService.atualizarAdolescente(idEntrevista, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{idEntrevista}/adolescentes/{idMatricula}")
    public  ResponseEntity<EntrevistaResponseDTO> removerAdolescente(@PathVariable Integer idEntrevista, @PathVariable Integer idMatricula) {
        EntrevistaResponseDTO response = entrevistaService.removerAdolescente(idEntrevista, idMatricula);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
