package com.projeto.cefom.controller;

import com.projeto.cefom.dto.request.JustificativaFaltaRequestDTO;
import com.projeto.cefom.dto.response.JustificativaFaltaListarResponseDTO;
import com.projeto.cefom.dto.response.JustificativaFaltaResponseDTO;
import com.projeto.cefom.service.JustificativaFaltaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adolescentes/{idAdolescente}/matriculas")
public class JustificativaFaltaController {

    private final JustificativaFaltaService service;

    public JustificativaFaltaController(JustificativaFaltaService justificativaFaltaService) {
        this.service = justificativaFaltaService;
    }

    @PostMapping("/{idMatricula}/justificativasfaltas")
    public ResponseEntity<JustificativaFaltaResponseDTO> criar(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula, @Valid @RequestBody JustificativaFaltaRequestDTO dto) {
        JustificativaFaltaResponseDTO response = service.criar(idAdolescente,idMatricula,dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{idMatricula}/justificativasfaltas/{idJustificativaFalta}")
    public ResponseEntity<JustificativaFaltaResponseDTO> atualizar(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula, @PathVariable Integer idJustificativaFalta, @Valid @RequestBody JustificativaFaltaRequestDTO dto) {
        JustificativaFaltaResponseDTO response = service.atualizar(idAdolescente,idMatricula,idJustificativaFalta,dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/justificativasfaltas")
    public ResponseEntity<Page<JustificativaFaltaListarResponseDTO>> listar(@PathVariable Integer idAdolescente, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<JustificativaFaltaListarResponseDTO> response = service.listar(idAdolescente, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idMatricula}/justificativasfaltas/{idJustificativaFalta}")
    public ResponseEntity<JustificativaFaltaResponseDTO> buscarPorId(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula, @PathVariable Integer idJustificativaFalta) {
        JustificativaFaltaResponseDTO response = service.buscarPorId(idAdolescente,idMatricula,idJustificativaFalta);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{idMatricula}/justificativasfaltas/{idJustificativaFalta}")
    public  ResponseEntity<Void> excluirPorId(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula, @PathVariable Integer idJustificativaFalta) {
        service.excluirPorId(idAdolescente,idMatricula,idJustificativaFalta);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
