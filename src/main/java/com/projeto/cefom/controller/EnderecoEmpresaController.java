package com.projeto.cefom.controller;

import com.projeto.cefom.dto.response.EnderecoListarResponseDTO;
import com.projeto.cefom.dto.response.EnderecoResponseDTO;
import com.projeto.cefom.service.EnderecoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empresas")
public class EnderecoEmpresaController {

    private final EnderecoService enderecoService;

    public EnderecoEmpresaController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping("/{idEmpresa}/enderecos")
    public ResponseEntity<Page<EnderecoListarResponseDTO>> listarEmpresa(@PathVariable Integer idEmpresa, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<EnderecoListarResponseDTO> response = enderecoService.listarEmpresa(idEmpresa, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idEmpresa}/enderecos/{idEndereco}")
    public ResponseEntity<EnderecoResponseDTO> buscarEmpresaPorId(@PathVariable Integer idEmpresa, @PathVariable Integer idEndereco) {
        EnderecoResponseDTO response = enderecoService.buscarEmpresaPorId(idEmpresa, idEndereco);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{idEmpresa}/enderecos/{idEndereco}")
    public ResponseEntity<Void> excluirEmpresaPorId(@PathVariable Integer idEmpresa, @PathVariable Integer idEndereco) {
        enderecoService.excluirEmpresaPorId(idEmpresa, idEndereco);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
