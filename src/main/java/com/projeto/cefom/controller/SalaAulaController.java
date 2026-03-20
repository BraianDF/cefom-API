package com.projeto.cefom.controller;

import com.projeto.cefom.dto.request.SalaAulaRequestDTO;
import com.projeto.cefom.dto.response.SalaAulaResponseDTO;
import com.projeto.cefom.service.SalaAulaService;
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
@RequestMapping("salasaulas")
public class SalaAulaController {

    private final SalaAulaService service;

    public SalaAulaController(SalaAulaService salaAulaService) {
        this.service = salaAulaService;
    }

    @PostMapping
    public ResponseEntity<SalaAulaResponseDTO> criar(@Valid @RequestBody SalaAulaRequestDTO dto) {
        SalaAulaResponseDTO response = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{idSalaAula}")
    public ResponseEntity<SalaAulaResponseDTO> atualizar(@PathVariable Integer idSalaAula, @Valid @RequestBody SalaAulaRequestDTO dto) {
        SalaAulaResponseDTO response = service.atualizar(idSalaAula, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<SalaAulaResponseDTO>> listar(@PageableDefault(page = 0, size = 10, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable, @RequestParam(defaultValue = "") String nome) {
        Page<SalaAulaResponseDTO> response = service.listar(pageable, nome);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/select")
    public ResponseEntity<List<SalaAulaResponseDTO>> listarSelect() {
        List<SalaAulaResponseDTO> response = service.listarSelect();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idSalaAula}")
    public ResponseEntity<SalaAulaResponseDTO> buscarPorId(@PathVariable Integer idSalaAula) {
        SalaAulaResponseDTO response = service.buscarPorId(idSalaAula);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{idSalaAula}")
    public  ResponseEntity<Void> excluirPorId(@PathVariable Integer idSalaAula) {
        service.excluirPorId(idSalaAula);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
