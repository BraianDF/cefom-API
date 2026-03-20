package com.projeto.cefom.controller;

import com.projeto.cefom.dto.request.AlunoParticipacaoRequestDTO;
import com.projeto.cefom.dto.request.TurmaRequestDTO;
import com.projeto.cefom.dto.response.TurmaListarResponseDTO;
import com.projeto.cefom.dto.response.TurmaResponseDTO;
import com.projeto.cefom.dto.response.TurmaSelectResponseDTO;
import com.projeto.cefom.service.TurmaService;
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
@RequestMapping("/turmas")
public class TurmaController {
    private final TurmaService turmaService;

    public TurmaController(TurmaService turmaService) {
        this.turmaService = turmaService;
    }

    @PostMapping
    public ResponseEntity<TurmaResponseDTO> criar(@Valid @RequestBody TurmaRequestDTO dto) {
        TurmaResponseDTO response = turmaService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{idTurma}")
    public ResponseEntity<TurmaResponseDTO> atualizar(@PathVariable Integer idTurma, @Valid @RequestBody TurmaRequestDTO dto) {
        TurmaResponseDTO response = turmaService.atualizar(idTurma, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<TurmaListarResponseDTO>> listar(@PageableDefault(page = 0, size = 10, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable, @RequestParam(defaultValue = "") String nome) {
        Page<TurmaListarResponseDTO> response = turmaService.listar(pageable, nome);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/select")
    public ResponseEntity<List<TurmaSelectResponseDTO>> listarSelect() {
        List<TurmaSelectResponseDTO> response = turmaService.listarSelect();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idTurma}")
    public ResponseEntity<TurmaResponseDTO> buscarPorId(@PathVariable Integer idTurma) {
        TurmaResponseDTO response = turmaService.buscarPorId(idTurma);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{idTurma}")
    public  ResponseEntity<Void> excluirPorId(@PathVariable Integer idTurma) {
        turmaService.excluirPorId(idTurma);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{idTurma}/adolescentes")
    public ResponseEntity<TurmaResponseDTO> adicionarAdolescente(@PathVariable Integer idTurma, @Valid @RequestBody AlunoParticipacaoRequestDTO dto) {
        TurmaResponseDTO response = turmaService.adicionarAdolescente(idTurma, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{idTurma}/adolescentes")
    public ResponseEntity<TurmaResponseDTO> removerAdolescente(@PathVariable Integer idTurma, @Valid @RequestBody AlunoParticipacaoRequestDTO dto) {
        TurmaResponseDTO response = turmaService.removerAdolescente(idTurma, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
