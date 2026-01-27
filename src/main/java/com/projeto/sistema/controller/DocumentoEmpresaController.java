package com.projeto.sistema.controller;


import com.projeto.sistema.service.DocumentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/empresas")
public class DocumentoEmpresaController {

    private final DocumentoService documentoService;

    public DocumentoEmpresaController(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }

    @DeleteMapping("/{idEmpresa}/documentos/{idDocumento}")
    public ResponseEntity<Void> excluir(@PathVariable Integer idEmpresa, @PathVariable Integer idDocumento) {
        documentoService.excluirEmpresaPorId(idEmpresa, idDocumento);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
