package com.projeto.sistema.controller;

import com.projeto.sistema.dto.request.SalarioAtualizarRequestDTO;
import com.projeto.sistema.dto.response.SalarioListarResponseDTO;
import com.projeto.sistema.dto.response.SalarioResponseDTO;
import com.projeto.sistema.service.SalarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adolescentes/{idAdolescente}/matriculas/{idMatricula}/contratos/{idContrato}")
public class SalarioController {

    private final SalarioService salarioService;

    public SalarioController(SalarioService salarioService) {
        this.salarioService = salarioService;
    }

    @PutMapping("/salarios")
    public ResponseEntity<SalarioResponseDTO> atualizar(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula, @PathVariable Integer idContrato, @Valid @RequestBody SalarioAtualizarRequestDTO dto) {
        SalarioResponseDTO response = salarioService.atualizar(idAdolescente, idMatricula, idContrato, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/salarios")
    public ResponseEntity<List<SalarioListarResponseDTO>> listar(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula, @PathVariable Integer idContrato) {
        List<SalarioListarResponseDTO> response = salarioService.listar(idAdolescente, idMatricula, idContrato);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/salarios/{idSalario}")
    public ResponseEntity<SalarioResponseDTO> buscarPorId(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula, @PathVariable Integer idContrato, @PathVariable Integer idSalario) {
        SalarioResponseDTO response = salarioService.buscarPorId(idAdolescente, idMatricula, idContrato, idSalario);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/salarios/{idSalario}")
    public ResponseEntity<Void> excluirPorId(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula, @PathVariable Integer idContrato, @PathVariable Integer idSalario) {
        salarioService.excluirPorId(idAdolescente, idMatricula, idContrato, idSalario);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
