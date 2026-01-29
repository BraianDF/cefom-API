package com.projeto.sistema.controller;

import com.projeto.sistema.dto.request.EmailAtualizarRequestDTO;
import com.projeto.sistema.dto.response.EmailListarResponseDTO;
import com.projeto.sistema.dto.response.EmailResponseDTO;
import com.projeto.sistema.service.EmailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresas")
public class EmailEmpresaController {
    private final EmailService emailService;

    public EmailEmpresaController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/{idEmpresa}/emails")
    public ResponseEntity<Page<EmailListarResponseDTO>> listar(@PathVariable Integer idEmpresa, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<EmailListarResponseDTO> response = emailService.listarEmpresa(idEmpresa, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idEmpresa}/emails/{idEmail}")
    public ResponseEntity<EmailResponseDTO> buscarPorId(@PathVariable Integer idEmpresa, @PathVariable Integer idEmail) {
        EmailResponseDTO response = emailService.buscarEmpresaPorId(idEmpresa, idEmail);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{idEmpresa}/emails/{idEmail}")
    public ResponseEntity<Void> excluirPorId(@PathVariable Integer idEmpresa, @PathVariable Integer idEmail) {
        emailService.excluirEmpresaPorId(idEmpresa, idEmail);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
