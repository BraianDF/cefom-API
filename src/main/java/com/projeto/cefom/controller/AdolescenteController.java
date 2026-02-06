package com.projeto.cefom.controller;

import com.projeto.cefom.dto.request.AdolescenteAtualizarRequestDTO;
import com.projeto.cefom.dto.response.AdolescenteListarResponseDTO;
import com.projeto.cefom.dto.response.AdolescenteMatriculaResponseDTO;
import com.projeto.cefom.dto.response.AdolescenteResponseDTO;
import com.projeto.cefom.service.AdolescenteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adolescentes")
public class AdolescenteController {
    private final AdolescenteService adolescenteService;

    public AdolescenteController(AdolescenteService adolescenteService) {
        this.adolescenteService = adolescenteService;
    }

    @PutMapping("/{idAdolescente}")
    public ResponseEntity<AdolescenteMatriculaResponseDTO> atualizar(@PathVariable Integer idAdolescente, @Valid @RequestBody AdolescenteAtualizarRequestDTO dto) {
        AdolescenteMatriculaResponseDTO response = adolescenteService.atualizar(idAdolescente, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<AdolescenteListarResponseDTO>> listar(@PageableDefault(page = 0, size = 10) Pageable pageable, @RequestParam(defaultValue = "") String nome) {
        Page<AdolescenteListarResponseDTO> response = adolescenteService.listar(pageable, nome);
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
