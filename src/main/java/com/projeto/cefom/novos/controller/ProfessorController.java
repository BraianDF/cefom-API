package com.projeto.cefom.novos.controller;

import com.projeto.cefom.novos.dto.request.ProfessorRequestDTO;
import com.projeto.cefom.novos.dto.response.ProfessorResponseDTO;
import com.projeto.cefom.novos.service.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("professores")
public class ProfessorController {

    private final ProfessorService service;

    public ProfessorController(ProfessorService professorService) {
        this.service = professorService;
    }

    @PostMapping
    public ResponseEntity<ProfessorResponseDTO> criar(@Valid @RequestBody ProfessorRequestDTO dto) {
        ProfessorResponseDTO response = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{idProfessor}")
    public ResponseEntity<ProfessorResponseDTO> atualizar(@PathVariable Integer idProfessor, @Valid @RequestBody ProfessorRequestDTO dto) {
        ProfessorResponseDTO response = service.atualizar(idProfessor, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<ProfessorResponseDTO>> listar(@PageableDefault(page = 0, size = 10) Pageable pageable, @RequestParam(defaultValue = "") String nome) {
        Page<ProfessorResponseDTO> response = service.listar(pageable, nome);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/select")
    public ResponseEntity<List<ProfessorResponseDTO>> listarSelect() {
        List<ProfessorResponseDTO> response = service.listarSelect();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idProfessor}")
    public ResponseEntity<ProfessorResponseDTO> buscarPorId(@PathVariable Integer idProfessor) {
        ProfessorResponseDTO response = service.buscarPorId(idProfessor);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{idProfessor}")
    public  ResponseEntity<Void> excluirPorId(@PathVariable Integer idProfessor) {
        service.excluirPorId(idProfessor);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
