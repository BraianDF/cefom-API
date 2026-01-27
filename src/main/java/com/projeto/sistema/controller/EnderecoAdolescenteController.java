package com.projeto.sistema.controller;

import com.projeto.sistema.dto.request.EnderecoAdolescenteAtualizarRequestDTO;
import com.projeto.sistema.dto.response.EnderecoListarResponseDTO;
import com.projeto.sistema.dto.response.EnderecoMatriculaResponseDTO;
import com.projeto.sistema.service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adolescentes")
public class EnderecoAdolescenteController {

    private final EnderecoService enderecoService;

    public EnderecoAdolescenteController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @PutMapping("/{idAdolescente}/enderecos")
    public ResponseEntity<EnderecoMatriculaResponseDTO> atualizarAdolescente(@PathVariable Integer idAdolescente, @Valid @RequestBody EnderecoAdolescenteAtualizarRequestDTO dto) {
        EnderecoMatriculaResponseDTO response = enderecoService.atualizarAdolescente(idAdolescente, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idAdolescente}/enderecos")
    public ResponseEntity<List<EnderecoListarResponseDTO>> listarAdolescente(@PathVariable Integer idAdolescente) {
        List<EnderecoListarResponseDTO> response = enderecoService.listarAdolescente(idAdolescente);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idAdolescente}/enderecos/{idEndereco}")
    public ResponseEntity<EnderecoMatriculaResponseDTO> buscarAdolescentePorId(@PathVariable Integer idAdolescente, @PathVariable Integer idEndereco) {
        EnderecoMatriculaResponseDTO response = enderecoService.buscarAdolescentePorId(idAdolescente, idEndereco);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{idAdolescente}/enderecos/{idEndereco}")
    public ResponseEntity<Void> excluirAdolescentePorId(@PathVariable Integer idAdolescente, @PathVariable Integer idEndereco) {
        enderecoService.excluirAdolescentePorId(idAdolescente, idEndereco);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
