package com.projeto.cefom.controller;

import com.projeto.cefom.dto.request.SalarioAtualizarRequestDTO;
import com.projeto.cefom.dto.response.SalarioListarResponseDTO;
import com.projeto.cefom.dto.response.SalarioResponseDTO;
import com.projeto.cefom.service.SalarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<SalarioListarResponseDTO>> listar(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula, @PathVariable Integer idContrato, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<SalarioListarResponseDTO> response = salarioService.listar(idAdolescente, idMatricula, idContrato, pageable);
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
