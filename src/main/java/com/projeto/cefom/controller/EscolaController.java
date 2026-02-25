package com.projeto.cefom.controller;

import com.projeto.cefom.dto.request.EscolaRequestDTO;
import com.projeto.cefom.dto.response.EscolaResponseDTO;
import com.projeto.cefom.dto.response.EscolaListarResponseDTO;
import com.projeto.cefom.dto.response.EscolaSelectResponseDTO;
import com.projeto.cefom.service.EscolaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/escolas")
public class EscolaController {
    private final EscolaService escolaService;

    public EscolaController(EscolaService escolaService) {
        this.escolaService = escolaService;
    }

    @PostMapping
    public ResponseEntity<EscolaResponseDTO> criar(@Valid @RequestBody EscolaRequestDTO dto) {
        EscolaResponseDTO response = escolaService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{idEscola}")
    public ResponseEntity<EscolaResponseDTO> atualizar(@PathVariable Integer idEscola, @Valid @RequestBody EscolaRequestDTO dto) {
        EscolaResponseDTO response = escolaService.atualizar(idEscola, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<EscolaListarResponseDTO>> listar(@PageableDefault(page = 0, size = 10, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable, @RequestParam(defaultValue = "") String nome) {
        Page<EscolaListarResponseDTO> response = escolaService.listar(pageable, nome);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/select")
    public ResponseEntity<List<EscolaSelectResponseDTO>> listarSelect() {
        List<EscolaSelectResponseDTO> response = escolaService.listarSelect();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idEscola}")
    public ResponseEntity<EscolaResponseDTO> buscarPorId(@PathVariable Integer idEscola) {
        EscolaResponseDTO response = escolaService.buscarPorId(idEscola);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{idEscola}")
    public ResponseEntity<Void> excluirPorId(@PathVariable Integer idEscola) {
        escolaService.excluirPorId(idEscola);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
