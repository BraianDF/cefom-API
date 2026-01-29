package com.projeto.sistema.controller;

import com.projeto.sistema.dto.response.ResponsavelEmpresaListarResponseDTO;
import com.projeto.sistema.dto.response.ResponsavelEmpresaResponseDTO;
import com.projeto.sistema.service.ResponsavelEmpresaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresas")
public class ResponsavelEmpresaController {

    private final ResponsavelEmpresaService responsavelEmpresaService;

    public ResponsavelEmpresaController(ResponsavelEmpresaService responsavelEmpresaService) {
        this.responsavelEmpresaService = responsavelEmpresaService;
    }

    @GetMapping("/{idEmpresa}/responsaveis")
    public ResponseEntity<Page<ResponsavelEmpresaListarResponseDTO>> listarEmpresa(@PathVariable Integer idEmpresa, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<ResponsavelEmpresaListarResponseDTO> response = responsavelEmpresaService.listarEmpresa(idEmpresa, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idEmpresa}/responsaveis/{idResponsavel}")
    public ResponseEntity<ResponsavelEmpresaResponseDTO> buscarEmpresaPorId(@PathVariable Integer idEmpresa, @PathVariable Integer idResponsavel) {
        ResponsavelEmpresaResponseDTO response = responsavelEmpresaService.buscarEmpresaPorId(idEmpresa, idResponsavel);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{idEmpresa}/responsaveis/{idResponsavel}")
    public ResponseEntity<Void> excluirEmpresaPorId(@PathVariable Integer idEmpresa, @PathVariable Integer idResponsavel) {
        responsavelEmpresaService.excluirEmpresaPorId(idEmpresa, idResponsavel);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
