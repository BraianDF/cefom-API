package com.projeto.sistema.controller;

import com.projeto.sistema.dto.request.EmailAtualizarRequestDTO;
import com.projeto.sistema.dto.response.EmailListarResponseDTO;
import com.projeto.sistema.dto.response.EmailResponseDTO;
import com.projeto.sistema.service.EmailService;
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
    public ResponseEntity<List<EmailListarResponseDTO>> listar(@PathVariable Integer idEmpresa) {
        List<EmailListarResponseDTO> response = emailService.listarEmpresa(idEmpresa);
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
