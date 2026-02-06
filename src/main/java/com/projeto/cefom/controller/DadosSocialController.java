package com.projeto.cefom.controller;

import com.projeto.cefom.dto.request.DadosSocialAtualizarRequestDTO;
import com.projeto.cefom.dto.response.DadosSocialListarResponseDTO;
import com.projeto.cefom.dto.response.DadosSocialResponseDTO;
import com.projeto.cefom.service.DadosSocialService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<DadosSocialListarResponseDTO>> listar(@PathVariable Integer idAdolescente, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<DadosSocialListarResponseDTO> response = dadosSocialService.listar(idAdolescente, pageable);
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
