package com.projeto.sistema.controller;

import com.projeto.sistema.dto.request.DadosSocialAtualizarRequestDTO;
import com.projeto.sistema.dto.response.DadosSocialListarResponseDTO;
import com.projeto.sistema.dto.response.DadosSocialResponseDTO;
import com.projeto.sistema.service.DadosSocialService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/adolescentes")
public class DadosSocialController {

    private final DadosSocialService dadosSocialService;

    public DadosSocialController(DadosSocialService dadosSocialService) {
        this.dadosSocialService = dadosSocialService;
    }

    @PutMapping("/{idAdolescente}/dadossociais")
    public ResponseEntity<DadosSocialResponseDTO> atualizar(@PathVariable Integer idAdolescente, @Valid @RequestBody DadosSocialAtualizarRequestDTO dto) {
        DadosSocialResponseDTO response = dadosSocialService.atualizar(idAdolescente, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idAdolescente}/dadossociais")
    public ResponseEntity<List<DadosSocialListarResponseDTO>> listar(@PathVariable Integer idAdolescente) {
        List<DadosSocialListarResponseDTO> response = dadosSocialService.listar(idAdolescente);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idAdolescente}/dadossociais/{idDadosSocial}")
    public ResponseEntity<DadosSocialResponseDTO> buscarPorId(@PathVariable Integer idAdolescente, @PathVariable Integer idDadosSocial) {
        DadosSocialResponseDTO response = dadosSocialService.buscarPorId(idAdolescente, idDadosSocial);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{idAdolescente}/dadossociais/{idDadosSocial}")
    public ResponseEntity<Void> excluirPorId(@PathVariable Integer idAdolescente, @PathVariable Integer idDadosSocial) {
        dadosSocialService.excluirPorId(idAdolescente, idDadosSocial);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
