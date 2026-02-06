package com.projeto.cefom.controller;

import com.projeto.cefom.dto.request.JornadaTrabalhoAtualizarRequestDTO;
import com.projeto.cefom.dto.response.JornadaTrabalhoListarResponseDTO;
import com.projeto.cefom.dto.response.JornadaTrabalhoResponseDTO;
import com.projeto.cefom.service.JornadaTrabalhoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adolescentes/{idAdolescente}/matriculas/{idMatricula}/contratos/{idContrato}")
public class JornadaTrabalhoController {

    private final JornadaTrabalhoService jornadaTrabalhoService;

    public JornadaTrabalhoController(JornadaTrabalhoService jornadaTrabalhoService) {
        this.jornadaTrabalhoService = jornadaTrabalhoService;
    }

    @PutMapping("/jornadastrabalhos")
    public ResponseEntity<JornadaTrabalhoResponseDTO> atualizar(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula, @PathVariable Integer idContrato, @Valid @RequestBody JornadaTrabalhoAtualizarRequestDTO dto) {
        JornadaTrabalhoResponseDTO response = jornadaTrabalhoService.atualizar(idAdolescente, idMatricula, idContrato, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/jornadastrabalhos")
    public ResponseEntity<Page<JornadaTrabalhoListarResponseDTO>> listar(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula, @PathVariable Integer idContrato, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<JornadaTrabalhoListarResponseDTO> response = jornadaTrabalhoService.listar(idAdolescente, idMatricula, idContrato, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/jornadastrabalhos/{idJornadaTrabalho}")
    public ResponseEntity<JornadaTrabalhoResponseDTO> buscarPorId(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula, @PathVariable Integer idContrato, @PathVariable Integer idJornadaTrabalho) {
        JornadaTrabalhoResponseDTO response = jornadaTrabalhoService.buscarPorId(idAdolescente, idMatricula, idContrato, idJornadaTrabalho);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/jornadastrabalhos/{idJornadaTrabalho}")
    public ResponseEntity<Void> excluirPorId(@PathVariable Integer idAdolescente, @PathVariable Integer idMatricula, @PathVariable Integer idContrato, @PathVariable Integer idJornadaTrabalho) {
        jornadaTrabalhoService.excluirPorId(idAdolescente, idMatricula, idContrato, idJornadaTrabalho);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
