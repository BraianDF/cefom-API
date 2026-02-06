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
@RequestMapping("/escolas")
public class EnderecoEscolaController {

    private final EnderecoService enderecoService;

    public EnderecoEscolaController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @GetMapping("/{idEscola}/enderecos")
    public ResponseEntity<Page<EnderecoListarResponseDTO>> listarEscola(@PathVariable Integer idEscola, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<EnderecoListarResponseDTO> response = enderecoService.listarEscola(idEscola, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idEscola}/enderecos/{idEndereco}")
    public ResponseEntity<EnderecoResponseDTO> buscarEscolaPorId(@PathVariable Integer idEscola, @PathVariable Integer idEndereco) {
        EnderecoResponseDTO response = enderecoService.buscarEscolaPorId(idEscola, idEndereco);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{idEscola}/enderecos/{idEndereco}")
    public ResponseEntity<Void> excluirEscolaPorId(@PathVariable Integer idEscola, @PathVariable Integer idEndereco) {
        enderecoService.excluirEscolaPorId(idEscola, idEndereco);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
