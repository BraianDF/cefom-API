package com.projeto.cefom.controller;

import com.projeto.cefom.dto.request.CursoRequestDTO;
import com.projeto.cefom.dto.response.CursoListarResponseDTO;
import com.projeto.cefom.dto.response.CursoResponseDTO;
import com.projeto.cefom.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<CursoListarResponseDTO>> listar(@PageableDefault(page = 0, size = 10, sort = "dataInicio", direction = Sort.Direction.DESC) Pageable pageable, @RequestParam(defaultValue = "") String nome) {
        Page<CursoListarResponseDTO> response = cursoService.listar(pageable, nome);
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
