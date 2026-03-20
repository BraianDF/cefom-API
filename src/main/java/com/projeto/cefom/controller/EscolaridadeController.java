package com.projeto.cefom.controller;

import com.projeto.cefom.dto.request.EscolaridadeAtualizarRequestDTO;
import com.projeto.cefom.dto.response.EscolaridadeListarResponseDTO;
import com.projeto.cefom.dto.response.EscolaridadeResponseDTO;
import com.projeto.cefom.service.EscolaridadeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<EscolaridadeListarResponseDTO>> listar(@PathVariable Integer idAdolescente, @PageableDefault(page = 0, size = 10, sort = "dataInicio", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<EscolaridadeListarResponseDTO> response = escolaridadeService.listar(idAdolescente, pageable);
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
