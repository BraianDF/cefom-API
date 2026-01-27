package com.projeto.sistema.controller;

import com.projeto.sistema.dto.request.DocumentoCpfRequestDTO;
import com.projeto.sistema.dto.request.MatriculaAtualizarRequestDTO;
import com.projeto.sistema.dto.request.MatriculaCriarRequestDTO;
import com.projeto.sistema.dto.request.MatriculaEncerrarRequestDTO;
import com.projeto.sistema.dto.response.*;
import com.projeto.sistema.service.MatriculaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/adolescentes")
public class MatriculaController {
    private final MatriculaService matriculaService;

    public MatriculaController(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    // OK
    @GetMapping("/matriculas/status")
    public ResponseEntity<StatusCpfMatriculaResponseDTO> verificarCpf(@Valid @RequestBody DocumentoCpfRequestDTO dto) {
        StatusCpfMatriculaResponseDTO response = matriculaService.verificarCpf(dto.cpf());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // OK
    @PostMapping("/matriculas")
    public ResponseEntity<MatriculaCriarResponseDTO> criar(@Valid @RequestBody MatriculaCriarRequestDTO dto) {
        MatriculaCriarResponseDTO response = matriculaService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // OK
    @PatchMapping("/{idAdolescente}/matriculas/{idMatricula}")
    public ResponseEntity<MatriculaResponseDTO> atualizar(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula, @Valid @RequestBody MatriculaAtualizarRequestDTO dto) {
        MatriculaResponseDTO response = matriculaService.atualizar(idAdolescente, idMatricula, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // OK
    @PatchMapping("/{idAdolescente}/matriculas/{idMatricula}/encerrar")
    public ResponseEntity<MatriculaResponseDTO> encerrar(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula, @Valid @RequestBody MatriculaEncerrarRequestDTO dto) {
        MatriculaResponseDTO response = matriculaService.encerrarPorId(idAdolescente, idMatricula, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // OK
    @GetMapping("/{idAdolescente}/matriculas")
    public ResponseEntity<List<MatriculaListarResponseDTO>> listarPorId(@PathVariable Integer idAdolescente) {
        List<MatriculaListarResponseDTO> response = matriculaService.listarPorId(idAdolescente);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // OK
    @GetMapping("/{idAdolescente}/matriculas/{idMatricula}")
    public ResponseEntity<MatriculaResponseDTO> buscarPorId(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula) {
        MatriculaResponseDTO response = matriculaService.buscarPorId(idAdolescente, idMatricula);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // OK
    @GetMapping("/matriculas")
    public ResponseEntity<List<MatriculaCriarResponseDTO>> listar() {
        List<MatriculaCriarResponseDTO> response = matriculaService.listar();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // OK
    @DeleteMapping("/{idAdolescente}/matriculas/{idMatricula}")
    public ResponseEntity<Void> excluir(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula) {
        matriculaService.excluirPorId(idAdolescente, idMatricula);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
