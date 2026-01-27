package com.projeto.sistema.controller;

import com.projeto.sistema.dto.request.EmailAtualizarRequestDTO;
import com.projeto.sistema.dto.response.EmailListarResponseDTO;
import com.projeto.sistema.dto.response.EmailResponseDTO;
import com.projeto.sistema.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/adolescentes")
public class EmailAdolescenteController {
    private final EmailService emailService;

    public EmailAdolescenteController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PutMapping("/{idAdolescente}/emails")
    public ResponseEntity<EmailResponseDTO> atualizar(@PathVariable Integer idAdolescente, @Valid @RequestBody EmailAtualizarRequestDTO dto) {
        EmailResponseDTO response = emailService.atualizar(idAdolescente, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idAdolescente}/emails")
    public ResponseEntity<List<EmailListarResponseDTO>> listar(@PathVariable Integer idAdolescente) {
        List<EmailListarResponseDTO> response = emailService.listar(idAdolescente);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idAdolescente}/emails/{idEmail}")
    public ResponseEntity<EmailResponseDTO> buscarPorId(@PathVariable Integer idAdolescente, @PathVariable Integer idEmail) {
        EmailResponseDTO response = emailService.buscarPorId(idAdolescente, idEmail);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{idAdolescente}/emails/{idEmail}")
    public ResponseEntity<Void> excluirPorId(@PathVariable Integer idAdolescente, @PathVariable Integer idEmail) {
        emailService.excluirPorId(idAdolescente, idEmail);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
