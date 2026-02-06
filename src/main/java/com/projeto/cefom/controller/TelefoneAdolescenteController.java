package com.projeto.cefom.controller;

import com.projeto.cefom.dto.request.TelefonesAtualizarRequestDTO;
import com.projeto.cefom.dto.response.*;
import com.projeto.cefom.service.TelefoneService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<TelefoneListarResponseDTO>> listar(@PathVariable Integer idAdolescente, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<TelefoneListarResponseDTO> response = telefoneService.listar(idAdolescente, pageable);
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
