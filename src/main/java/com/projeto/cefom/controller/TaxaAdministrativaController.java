package com.projeto.cefom.controller;

import com.projeto.cefom.dto.response.TaxaAdministrativaListarResponseDTO;
import com.projeto.cefom.dto.response.TaxaAdministrativaResponseDTO;
import com.projeto.cefom.service.TaxaAdministrativaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empresas")
public class TaxaAdministrativaController {
    private final TaxaAdministrativaService taxaAdministrativaService;

    public TaxaAdministrativaController(TaxaAdministrativaService taxaAdministrativaService) {
        this.taxaAdministrativaService = taxaAdministrativaService;
    }

    @GetMapping("/{idEmpresa}/taxasadministrativas")
    public ResponseEntity<Page<TaxaAdministrativaListarResponseDTO>> listar(@PathVariable Integer idEmpresa, @PageableDefault(page = 0, size = 10, sort = "dataInicio", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<TaxaAdministrativaListarResponseDTO> response = taxaAdministrativaService.listarEmpresa(idEmpresa, pageable);
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
