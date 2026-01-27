package com.projeto.sistema.controller;

import com.projeto.sistema.dto.request.TelefonesAtualizarRequestDTO;
import com.projeto.sistema.dto.response.*;
import com.projeto.sistema.service.TelefoneService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adolescentes")
public class TelefoneAdolescenteController {
    private final TelefoneService telefoneService;

    public TelefoneAdolescenteController(TelefoneService telefoneService) {
        this.telefoneService = telefoneService;
    }

    @PutMapping("/{idAdolescente}/telefones")
    public ResponseEntity<TelefonesResponseDTO> atualizar(@PathVariable Integer idAdolescente, @Valid @RequestBody TelefonesAtualizarRequestDTO dto) {
        TelefonesResponseDTO response = telefoneService.atualizar(idAdolescente, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idAdolescente}/telefones")
    public ResponseEntity<List<TelefoneListarResponseDTO>> listar(@PathVariable Integer idAdolescente) {
        List<TelefoneListarResponseDTO> response = telefoneService.listar(idAdolescente);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idAdolescente}/telefones/{idTelefone}")
    public ResponseEntity<TelefoneResponseDTO> buscarPorId(@PathVariable Integer idAdolescente, @PathVariable Integer idTelefone) {
        TelefoneResponseDTO response = telefoneService.buscarPorId(idAdolescente, idTelefone);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{idAdolescente}/telefones/{idTelefone}")
    public ResponseEntity<Void> excluirPorId(@PathVariable Integer idAdolescente, @PathVariable Integer idTelefone) {
        telefoneService.excluirPorId(idAdolescente, idTelefone);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
