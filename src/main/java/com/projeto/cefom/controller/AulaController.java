package com.projeto.cefom.controller;

import com.projeto.cefom.dto.request.AulaRequestDTO;
import com.projeto.cefom.dto.response.AulaListarResponseDTO;
import com.projeto.cefom.dto.response.AulaResponseDTO;
import com.projeto.cefom.service.AulaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("aulas")
public class AulaController {

    private final AulaService service;

    public AulaController(AulaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AulaResponseDTO> criar(@Valid @RequestBody AulaRequestDTO dto) {
        AulaResponseDTO response = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{idAula}")
    public ResponseEntity<AulaResponseDTO> atualizar(@PathVariable Integer idAula, @Valid @RequestBody AulaRequestDTO dto) {
        AulaResponseDTO response = service.atualizar(idAula, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<AulaListarResponseDTO>> listar(@PageableDefault(page = 0, size = 10) Pageable pageable, @RequestParam(defaultValue = "") String nome) {
        Page<AulaListarResponseDTO> response = service.listar(pageable, nome);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idAula}")
    public ResponseEntity<AulaResponseDTO> buscarPorId(@PathVariable Integer idAula) {
        AulaResponseDTO response = service.buscarPorId(idAula);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{idAula}")
    public  ResponseEntity<Void> excluirPorId(@PathVariable Integer idAula) {
        service.excluirPorId(idAula);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
