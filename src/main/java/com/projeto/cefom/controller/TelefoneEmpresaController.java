package com.projeto.cefom.controller;

import com.projeto.cefom.dto.response.TelefoneListarResponseDTO;
import com.projeto.cefom.dto.response.TelefoneResponseDTO;
import com.projeto.cefom.service.TelefoneService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empresas")
public class TelefoneEmpresaController {
    private final TelefoneService telefoneService;

    public TelefoneEmpresaController(TelefoneService telefoneService) {
        this.telefoneService = telefoneService;
    }

    @GetMapping("/{idEmpresa}/telefones")
    public ResponseEntity<Page<TelefoneListarResponseDTO>> listarEmpresa(@PathVariable Integer idEmpresa, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<TelefoneListarResponseDTO> response = telefoneService.listarEmpresa(idEmpresa, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idEmpresa}/telefones/{idTelefone}")
    public ResponseEntity<TelefoneResponseDTO> buscarEmpresaPorId(@PathVariable Integer idEmpresa, @PathVariable Integer idTelefone) {
        TelefoneResponseDTO response = telefoneService.buscarEmpresaPorId(idEmpresa, idTelefone);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{idEmpresa}/telefones/{idTelefone}")
    public ResponseEntity<Void> excluirEmpresaPorId(@PathVariable Integer idEmpresa, @PathVariable Integer idTelefone) {
        telefoneService.excluirEmpresaPorId(idEmpresa, idTelefone);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
