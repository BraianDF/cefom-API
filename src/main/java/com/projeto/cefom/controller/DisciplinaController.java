package com.projeto.cefom.controller;

import com.projeto.cefom.dto.request.DisciplinaRequestDTO;
import com.projeto.cefom.dto.response.DisciplinaResponseDTO;
import com.projeto.cefom.service.DisciplinaService;
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
@RequestMapping("/disciplinas")
public class DisciplinaController {

    private final DisciplinaService service;

    public DisciplinaController(DisciplinaService disciplinaService) {
        this.service = disciplinaService;
    }

    @PostMapping
    public ResponseEntity<DisciplinaResponseDTO> criar(@Valid @RequestBody DisciplinaRequestDTO dto) {
        DisciplinaResponseDTO response = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{idDisciplina}")
    public ResponseEntity<DisciplinaResponseDTO> atualizar(@PathVariable Integer idDisciplina, @Valid @RequestBody DisciplinaRequestDTO dto) {
        DisciplinaResponseDTO response = service.atualizar(idDisciplina, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<DisciplinaResponseDTO>> listar(@PageableDefault(page = 0, size = 10, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable, @RequestParam(defaultValue = "") String nome) {
        Page<DisciplinaResponseDTO> response = service.listar(pageable, nome);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/select")
    public ResponseEntity<List<DisciplinaResponseDTO>> listarSelect() {
        List<DisciplinaResponseDTO> response = service.listarSelect();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idDisciplina}")
    public ResponseEntity<DisciplinaResponseDTO> buscarPorId(@PathVariable Integer idDisciplina) {
        DisciplinaResponseDTO response = service.buscarPorId(idDisciplina);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{idDisciplina}")
    public  ResponseEntity<Void> excluirPorId(@PathVariable Integer idDisciplina) {
        service.excluirPorId(idDisciplina);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
