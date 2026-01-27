package com.projeto.sistema.controller;

import com.projeto.sistema.dto.response.TaxaAdministrativaListarResponseDTO;
import com.projeto.sistema.dto.response.TaxaAdministrativaResponseDTO;
import com.projeto.sistema.service.TaxaAdministrativaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresas")
public class TaxaAdministrativaController {
    private final TaxaAdministrativaService taxaAdministrativaService;

    public TaxaAdministrativaController(TaxaAdministrativaService taxaAdministrativaService) {
        this.taxaAdministrativaService = taxaAdministrativaService;
    }

    @GetMapping("/{idEmpresa}/taxasadministrativas")
    public ResponseEntity<List<TaxaAdministrativaListarResponseDTO>> listar(@PathVariable Integer idEmpresa) {
        List<TaxaAdministrativaListarResponseDTO> response = taxaAdministrativaService.listarEmpresa(idEmpresa);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idEmpresa}/taxasadministrativas/{idTaxaAdministrativa}")
    public ResponseEntity<TaxaAdministrativaResponseDTO> buscarPorId(@PathVariable Integer idEmpresa, @PathVariable Integer idTaxaAdministrativa) {
        TaxaAdministrativaResponseDTO response = taxaAdministrativaService.buscarEmpresaPorId(idEmpresa, idTaxaAdministrativa);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{idEmpresa}/taxasadministrativas/{idTaxaAdministrativa}")
    public ResponseEntity<Void> excluirPorId(@PathVariable Integer idEmpresa, @PathVariable Integer idTaxaAdministrativa) {
        taxaAdministrativaService.excluirEmpresaPorId(idEmpresa, idTaxaAdministrativa);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
