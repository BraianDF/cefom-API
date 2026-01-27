package com.projeto.sistema.controller;

import com.projeto.sistema.dto.request.EscolaridadeAtualizarRequestDTO;
import com.projeto.sistema.dto.request.FamiliaresAtualizarRequestDTO;
import com.projeto.sistema.dto.response.*;
import com.projeto.sistema.service.FamiliarService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adolescentes")
public class FamiliarController {
    private final FamiliarService familiarService;

    public FamiliarController(FamiliarService familiarService) {
        this.familiarService = familiarService;
    }

    @PutMapping("/{idAdolescente}/familiares")
    public ResponseEntity<FamiliaresResponseDTO> atualizar(@PathVariable Integer idAdolescente, @Valid @RequestBody FamiliaresAtualizarRequestDTO dto) {
        FamiliaresResponseDTO response = familiarService.atualizar(idAdolescente, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idAdolescente}/familiares")
    public ResponseEntity<List<FamiliarListarResponseDTO>> listar(@PathVariable Integer idAdolescente) {
        List<FamiliarListarResponseDTO> response = familiarService.listar(idAdolescente);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idAdolescente}/familiares/{idFamiliar}")
    public ResponseEntity<ResponsavelResponseDTO> buscarPorId(@PathVariable Integer idAdolescente, @PathVariable Integer idFamiliar) {
        ResponsavelResponseDTO response = familiarService.buscarPorId(idAdolescente, idFamiliar);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{idAdolescente}/familiares/{idFamiliar}")
    public ResponseEntity<Void> excluirPorId(@PathVariable Integer idAdolescente, @PathVariable Integer idFamiliar) {
        familiarService.excluirPorId(idAdolescente, idFamiliar);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
