package com.projeto.sistema.controller;

import com.projeto.sistema.dto.response.ResponsavelEmpresaListarResponseDTO;
import com.projeto.sistema.dto.response.ResponsavelEmpresaResponseDTO;
import com.projeto.sistema.service.ResponsavelEmpresaService;
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
    public ResponseEntity<List<ResponsavelEmpresaListarResponseDTO>> listarEmpresa(@PathVariable Integer idEmpresa) {
        List<ResponsavelEmpresaListarResponseDTO> response = responsavelEmpresaService.listarEmpresa(idEmpresa);
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
