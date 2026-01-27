package com.projeto.sistema.controller;

import com.projeto.sistema.dto.request.AdolescenteAtualizarRequestDTO;
import com.projeto.sistema.dto.response.AdolescenteListarResponseDTO;
import com.projeto.sistema.dto.response.AdolescenteMatriculaResponseDTO;
import com.projeto.sistema.dto.response.AdolescenteResponseDTO;
import com.projeto.sistema.dto.response.EmpresaCriarResponseDTO;
import com.projeto.sistema.service.AdolescenteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adolescentes")
public class AdolescenteController {
    private final AdolescenteService adolescenteService;

    public AdolescenteController(AdolescenteService adolescenteService) {
        this.adolescenteService = adolescenteService;
    }

    @PatchMapping("/{idAdolescente}")
    public ResponseEntity<AdolescenteMatriculaResponseDTO> atualizar(@PathVariable Integer idAdolescente, @Valid @RequestBody AdolescenteAtualizarRequestDTO dto) {
        AdolescenteMatriculaResponseDTO response = adolescenteService.atualizar(idAdolescente, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AdolescenteListarResponseDTO>> listar() {
        List<AdolescenteListarResponseDTO> response = adolescenteService.listar();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idAdolescente}")
    public ResponseEntity<AdolescenteResponseDTO> buscarPorId(@PathVariable Integer idAdolescente) {
        AdolescenteResponseDTO response = adolescenteService.buscarPorId(idAdolescente);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{idAdolescente}")
    public ResponseEntity<Void> excluir(@PathVariable Integer idAdolescente) {
        adolescenteService.excluirPorId(idAdolescente);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
