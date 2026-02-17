package com.projeto.cefom.novos.controller;

import com.projeto.cefom.novos.dto.request.ChamadaRequestDTO;
import com.projeto.cefom.novos.dto.response.ChamadaResponseDTO;
import com.projeto.cefom.novos.service.ChamadaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("aulas/{idAula}/chamada")
public class ChamadaController {

    private final ChamadaService service;

    public ChamadaController(ChamadaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ChamadaResponseDTO> criar(@PathVariable Integer idAula, @Valid @RequestBody ChamadaRequestDTO dto) {
        ChamadaResponseDTO response = service.criar(idAula, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<ChamadaResponseDTO> atualizar(@PathVariable Integer idAula, @Valid @RequestBody ChamadaRequestDTO dto) {
        ChamadaResponseDTO response = service.atualizar(idAula, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<ChamadaResponseDTO> listar(@PathVariable Integer idAula) {
        ChamadaResponseDTO response = service.listar(idAula);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping()
    public  ResponseEntity<Void> excluirPorId(@PathVariable Integer idAula) {
        service.excluirPorId(idAula);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
