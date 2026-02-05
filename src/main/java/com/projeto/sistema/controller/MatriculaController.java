package com.projeto.sistema.controller;

import com.projeto.sistema.dto.request.DocumentoCpfRequestDTO;
import com.projeto.sistema.dto.request.MatriculaAtualizarRequestDTO;
import com.projeto.sistema.dto.request.MatriculaCriarRequestDTO;
import com.projeto.sistema.dto.request.MatriculaEncerrarRequestDTO;
import com.projeto.sistema.dto.response.*;
import com.projeto.sistema.service.MatriculaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/adolescentes")
public class MatriculaController {
    private final MatriculaService matriculaService;

    public MatriculaController(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    @PostMapping("/matriculas/status")
    public ResponseEntity<StatusCpfMatriculaResponseDTO> verificarCpf(@Valid @RequestBody DocumentoCpfRequestDTO dto) {
        StatusCpfMatriculaResponseDTO response = matriculaService.verificarCpf(dto.cpf());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(value = "/matriculas", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MatriculaCriarResponseDTO> criar(@Valid @RequestPart("dados") MatriculaCriarRequestDTO dto, @RequestPart(value = "file", required = false) MultipartFile file) {
        MatriculaCriarResponseDTO response = matriculaService.criar(dto, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(value = "/{idAdolescente}/matriculas/{idMatricula}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MatriculaResponseDTO> atualizar(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula, @Valid @RequestPart("dados") MatriculaAtualizarRequestDTO dto, @RequestPart(value = "file", required = false) MultipartFile file) {
        MatriculaResponseDTO response = matriculaService.atualizar(idAdolescente, idMatricula, dto, file);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{idAdolescente}/matriculas/{idMatricula}/encerrar")
    public ResponseEntity<MatriculaResponseDTO> encerrar(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula, @Valid @RequestBody MatriculaEncerrarRequestDTO dto) {
        MatriculaResponseDTO response = matriculaService.encerrarPorId(idAdolescente, idMatricula, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idAdolescente}/matriculas")
    public ResponseEntity<Page<MatriculaListarResponseDTO>> listarPorId(@PathVariable Integer idAdolescente, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<MatriculaListarResponseDTO> response = matriculaService.listarPorId(idAdolescente, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/matriculas/select")
    public ResponseEntity<List<MatriculaSelectResponseDTO>> listarSelect() {
        List<MatriculaSelectResponseDTO> response = matriculaService.listarSelect();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idAdolescente}/matriculas/{idMatricula}")
    public ResponseEntity<MatriculaResponseDTO> buscarPorId(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula) {
        MatriculaResponseDTO response = matriculaService.buscarPorId(idAdolescente, idMatricula);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/matriculas")
    public ResponseEntity<List<MatriculaCriarResponseDTO>> listar() {
        List<MatriculaCriarResponseDTO> response = matriculaService.listar();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{idAdolescente}/matriculas/{idMatricula}")
    public ResponseEntity<Void> excluir(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula) {
        matriculaService.excluirPorId(idAdolescente, idMatricula);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
