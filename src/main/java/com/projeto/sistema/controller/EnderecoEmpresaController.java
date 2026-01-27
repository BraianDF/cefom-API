package com.projeto.sistema.controller;

import com.projeto.sistema.dto.response.EnderecoListarResponseDTO;
import com.projeto.sistema.dto.response.EnderecoResponseDTO;
import com.projeto.sistema.service.EnderecoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresas")
public class EnderecoEmpresaController {

    private final EnderecoService enderecoService;

    public EnderecoEmpresaController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping("/{idEmpresa}/enderecos")
    public ResponseEntity<List<EnderecoListarResponseDTO>> listarEmpresa(@PathVariable Integer idEmpresa) {
        List<EnderecoListarResponseDTO> response = enderecoService.listarEmpresa(idEmpresa);
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
