package com.projeto.cefom.controller;

import com.projeto.cefom.dto.request.EmailAtualizarRequestDTO;
import com.projeto.cefom.dto.response.EmailListarResponseDTO;
import com.projeto.cefom.dto.response.EmailResponseDTO;
import com.projeto.cefom.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<EmailListarResponseDTO>> listar(@PathVariable Integer idAdolescente, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<EmailListarResponseDTO> response = emailService.listar(idAdolescente, pageable);
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
