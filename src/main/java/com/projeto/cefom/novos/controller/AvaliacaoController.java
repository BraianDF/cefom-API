package com.projeto.cefom.novos.controller;

import com.projeto.cefom.novos.dto.request.ChamadaAvaliacaoRequestDTO;
import com.projeto.cefom.novos.dto.request.ChamadaRequestDTO;
import com.projeto.cefom.novos.dto.response.ChamadaAvaliacaoResponseDTO;
import com.projeto.cefom.novos.dto.response.ChamadaResponseDTO;
import com.projeto.cefom.novos.service.ChamadaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("aulas/{idAula}/avaliacao")
public class AvaliacaoController {

    private final ChamadaService service;

    public AvaliacaoController(ChamadaService service) {
        this.service = service;
    }

    @PutMapping
    public ResponseEntity<ChamadaAvaliacaoResponseDTO> atualizar(@PathVariable Integer idAula, @Valid @RequestBody ChamadaAvaliacaoRequestDTO dto) {
        ChamadaAvaliacaoResponseDTO response = service.atualizarAvaliacao(idAula, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<ChamadaAvaliacaoResponseDTO> listar(@PathVariable Integer idAula) {
        ChamadaAvaliacaoResponseDTO response = service.listarAvaliacao(idAula);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
