package com.projeto.cefom.controller;

import com.projeto.cefom.dto.request.ContratoAtualizarRequestDTO;
import com.projeto.cefom.dto.request.ContratoEncerrarRequestDTO;
import com.projeto.cefom.dto.request.ContratoRequestDTO;
import com.projeto.cefom.dto.response.ContratoListarResponseDTO;
import com.projeto.cefom.dto.response.ContratoResponseDTO;
import com.projeto.cefom.service.ContratoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adolescentes/{idAdolescente}/matriculas")
public class ContratoController {
    private final ContratoService contratoService;

    public ContratoController(ContratoService contratoService) {
        this.contratoService = contratoService;
    }

    @PostMapping("/{idMatricula}/contratos")
    public ResponseEntity<ContratoResponseDTO> criar(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula, @Valid @RequestBody ContratoRequestDTO dto) {
        ContratoResponseDTO response = contratoService.criar(idAdolescente, idMatricula, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{idMatricula}/contratos/{idContrato}")
    public ResponseEntity<ContratoResponseDTO> atualizar(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula, @PathVariable Integer idContrato, @Valid  @RequestBody ContratoAtualizarRequestDTO dto) {
        ContratoResponseDTO response = contratoService.atualizar(idAdolescente, idMatricula, idContrato, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{idMatricula}/contratos/{idContrato}")
    public ResponseEntity<ContratoResponseDTO> encerrar(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula, @PathVariable Integer idContrato, @Valid  @RequestBody ContratoEncerrarRequestDTO dto) {
        ContratoResponseDTO response = contratoService.encerrar(idAdolescente, idMatricula, idContrato, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/contratos")
    public ResponseEntity<Page<ContratoListarResponseDTO>> listar(@PathVariable Integer idAdolescente, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<ContratoListarResponseDTO> response = contratoService.listar(idAdolescente, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idMatricula}/contratos/{idContrato}")
    public ResponseEntity<ContratoResponseDTO> buscarPorId(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula, @PathVariable Integer idContrato) {
        ContratoResponseDTO response = contratoService.buscarPorId(idAdolescente, idMatricula, idContrato);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{idMatricula}/contratos/{idContrato}")
    public  ResponseEntity<Void> excluirPorId(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula, @PathVariable Integer idContrato) {
        contratoService.excluirPorId(idAdolescente, idMatricula, idContrato);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
