package com.projeto.sistema.controller;

import com.projeto.sistema.dto.request.CursoRequestDTO;
import com.projeto.sistema.dto.response.CursoListarResponseDTO;
import com.projeto.sistema.dto.response.CursoResponseDTO;
import com.projeto.sistema.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {
    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @PostMapping
    public ResponseEntity<CursoResponseDTO> criar(@Valid @RequestBody CursoRequestDTO dto) {
        CursoResponseDTO response = cursoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{idCurso}")
    public ResponseEntity<CursoResponseDTO> atualizar(@PathVariable Integer idCurso, @Valid @RequestBody CursoRequestDTO dto) {
        CursoResponseDTO response = cursoService.atualizar(idCurso, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CursoListarResponseDTO>> listar() {
        List<CursoListarResponseDTO> response = cursoService.listar();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idCurso}")
    public ResponseEntity<CursoResponseDTO> buscarPorId(@PathVariable Integer idCurso) {
        CursoResponseDTO response = cursoService.buscarPorId(idCurso);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{idCurso}")
    public  ResponseEntity<Void> excluirPorId(@PathVariable Integer idCurso) {
        cursoService.excluirPorId(idCurso);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
