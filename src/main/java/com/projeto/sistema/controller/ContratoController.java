package com.projeto.sistema.controller;

import com.projeto.sistema.dto.request.ContratoAtualizarRequestDTO;
import com.projeto.sistema.dto.request.ContratoEncerrarRequestDTO;
import com.projeto.sistema.dto.request.ContratoRequestDTO;
import com.projeto.sistema.dto.response.ContratoListarResponseDTO;
import com.projeto.sistema.dto.response.ContratoResponseDTO;
import com.projeto.sistema.service.ContratoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<ContratoListarResponseDTO>> listar(@PathVariable Integer idAdolescente) {
        List<ContratoListarResponseDTO> response = contratoService.listar(idAdolescente);
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
