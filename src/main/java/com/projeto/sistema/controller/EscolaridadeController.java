package com.projeto.sistema.controller;

import com.projeto.sistema.dto.request.EscolaridadeAtualizarRequestDTO;
import com.projeto.sistema.dto.request.EscolaridadeRequestDTO;
import com.projeto.sistema.dto.response.EscolaridadeListarResponseDTO;
import com.projeto.sistema.dto.response.EscolaridadeResponseDTO;
import com.projeto.sistema.service.EscolaridadeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adolescentes")
public class EscolaridadeController {

    private final EscolaridadeService escolaridadeService;

    public EscolaridadeController(EscolaridadeService escolaridadeService) {
        this.escolaridadeService = escolaridadeService;
    }

    @PutMapping("/{idAdolescente}/escolaridades")
    public ResponseEntity<EscolaridadeResponseDTO> atualizar(@PathVariable Integer idAdolescente, @Valid @RequestBody EscolaridadeAtualizarRequestDTO dto) {
        EscolaridadeResponseDTO response = escolaridadeService.atualizar(idAdolescente, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idAdolescente}/escolaridades")
    public ResponseEntity<List<EscolaridadeListarResponseDTO>> listar(@PathVariable Integer idAdolescente) {
        List<EscolaridadeListarResponseDTO> response = escolaridadeService.listar(idAdolescente);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idAdolescente}/escolaridades/{idEscolaridade}")
    public ResponseEntity<EscolaridadeResponseDTO> buscarPorId(@PathVariable Integer idAdolescente, @PathVariable Integer idEscolaridade) {
        EscolaridadeResponseDTO response = escolaridadeService.buscarPorId(idAdolescente, idEscolaridade);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{idAdolescente}/escolaridades/{idEscolaridade}")
    public ResponseEntity<Void> excluirPorId(@PathVariable Integer idAdolescente, @PathVariable Integer idEscolaridade) {
        escolaridadeService.excluirPorId(idAdolescente, idEscolaridade);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
