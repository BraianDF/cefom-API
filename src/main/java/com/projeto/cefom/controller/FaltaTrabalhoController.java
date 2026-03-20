package com.projeto.cefom.controller;

import com.projeto.cefom.dto.request.FaltaTrabalhoRequestDTO;
import com.projeto.cefom.dto.response.FaltaTrabalhoListarResponseDTO;
import com.projeto.cefom.dto.response.FaltaTrabalhoResponseDTO;
import com.projeto.cefom.service.FaltaTrabalhoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adolescentes/{idAdolescente}/matriculas/{idMatricula}/contratos")
public class FaltaTrabalhoController {

    private final FaltaTrabalhoService service;

    public FaltaTrabalhoController(FaltaTrabalhoService faltaTrabalhoService) {
        this.service = faltaTrabalhoService;
    }

    @PostMapping("/{idContrato}/faltastrabalhos")
    public ResponseEntity<FaltaTrabalhoResponseDTO> criar(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula, @PathVariable Integer idContrato, @Valid @RequestBody FaltaTrabalhoRequestDTO dto) {
        FaltaTrabalhoResponseDTO response = service.criar(idAdolescente,idMatricula,idContrato,dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{idContrato}/faltastrabalhos/{idFaltaTrabalho}")
    public ResponseEntity<FaltaTrabalhoResponseDTO> atualizar(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula, @PathVariable Integer idContrato, @PathVariable Integer idFaltaTrabalho, @Valid @RequestBody FaltaTrabalhoRequestDTO dto) {
        FaltaTrabalhoResponseDTO response = service.atualizar(idAdolescente,idMatricula,idContrato,idFaltaTrabalho,dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idContrato}/faltastrabalhos")
    public ResponseEntity<Page<FaltaTrabalhoListarResponseDTO>> listar(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula, @PathVariable Integer idContrato, @PageableDefault(page = 0, size = 10, sort = "dataInicio", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<FaltaTrabalhoListarResponseDTO> response = service.listar(idAdolescente,idMatricula,idContrato,pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idContrato}/faltastrabalhos/{idFaltaTrabalho}")
    public ResponseEntity<FaltaTrabalhoResponseDTO> buscarPorId(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula, @PathVariable Integer idContrato, @PathVariable Integer idFaltaTrabalho) {
        FaltaTrabalhoResponseDTO response = service.buscarPorId(idAdolescente,idMatricula,idContrato,idFaltaTrabalho);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{idContrato}/faltastrabalhos/{idFaltaTrabalho}")
    public  ResponseEntity<Void> excluirPorId(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula, @PathVariable Integer idContrato, @PathVariable Integer idFaltaTrabalho) {
        service.excluirPorId(idAdolescente,idMatricula,idContrato,idFaltaTrabalho);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
