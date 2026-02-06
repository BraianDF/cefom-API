package com.projeto.cefom.controller;


import com.projeto.cefom.dto.request.DocumentoAdolescenteRequestDTO;
import com.projeto.cefom.dto.response.DocumentoAdolescenteResponseDTO;
import com.projeto.cefom.service.DocumentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adolescentes")
public class DocumentoAdolescenteController {

    private final DocumentoService documentoService;

    public DocumentoAdolescenteController(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }

    @PutMapping("/{idAdolescente}/documentos")
    public ResponseEntity<DocumentoAdolescenteResponseDTO> atualizar(@PathVariable Integer idAdolescente, @Valid @RequestBody DocumentoAdolescenteRequestDTO dto) {
        DocumentoAdolescenteResponseDTO response = documentoService.atualizar(idAdolescente, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{idAdolescente}/documentos/{idDocumento}")
    public ResponseEntity<Void> excluir(@PathVariable Integer idAdolescente, @PathVariable Integer idDocumento) {
        documentoService.excluirPorId(idAdolescente, idDocumento);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
