package com.projeto.cefom.controller;

import com.projeto.cefom.dto.request.AfastamentoRequestDTO;
import com.projeto.cefom.dto.response.AfastamentoListarResponseDTO;
import com.projeto.cefom.dto.response.AfastamentoResponseDTO;
import com.projeto.cefom.service.AfastamentoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adolescentes/{idAdolescente}/matriculas/{idMatricula}/contratos/{idContrato}")
public class AfastamentoController {

    private final AfastamentoService afastamentoService;

    public AfastamentoController(AfastamentoService afastamentoService) {
        this.afastamentoService = afastamentoService;
    }

    @PostMapping("/afastamentos")
    public ResponseEntity<AfastamentoResponseDTO> criar(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula, @PathVariable Integer idContrato, @Valid @RequestBody AfastamentoRequestDTO dto) {
        AfastamentoResponseDTO response = afastamentoService.criar(idAdolescente, idMatricula, idContrato, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/afastamentos/{idAfastamento}")
    public ResponseEntity<AfastamentoResponseDTO> atualizar(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula, @PathVariable Integer idContrato, @PathVariable Integer idAfastamento, @Valid @RequestBody AfastamentoRequestDTO dto) {
        AfastamentoResponseDTO response = afastamentoService.atualizar(idAdolescente, idMatricula, idContrato, idAfastamento, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/afastamentos")
    public ResponseEntity<Page<AfastamentoListarResponseDTO>> listar(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula, @PathVariable Integer idContrato, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<AfastamentoListarResponseDTO> response = afastamentoService.listar(idAdolescente, idMatricula, idContrato, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/afastamentos/{idAfastamento}")
    public ResponseEntity<AfastamentoResponseDTO> buscarPorId(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula, @PathVariable Integer idContrato, @PathVariable Integer idAfastamento) {
        AfastamentoResponseDTO response = afastamentoService.buscarPorId(idAdolescente, idMatricula, idContrato, idAfastamento);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/afastamentos/{idAfastamento}")
    public ResponseEntity<Void> excluirPorId(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula, @PathVariable Integer idContrato, @PathVariable Integer idAfastamento) {
        afastamentoService.excluirPorId(idAdolescente, idMatricula, idContrato, idAfastamento);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
