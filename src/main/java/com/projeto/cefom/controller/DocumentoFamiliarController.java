package com.projeto.cefom.controller;


import com.projeto.cefom.service.DocumentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adolescentes")
public class DocumentoFamiliarController {

    private final DocumentoService documentoService;

    public DocumentoFamiliarController(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }

    @DeleteMapping("/{idAdolescente}/familiares/{idFamiliar}/documentos/{idDocumento}")
    public ResponseEntity<Void> excluir(@PathVariable Integer idAdolescente, @PathVariable Integer idFamiliar, @PathVariable Integer idDocumento) {
        documentoService.excluirFamiliarPorId(idAdolescente, idFamiliar, idDocumento);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
