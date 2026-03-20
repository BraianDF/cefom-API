package com.projeto.cefom.controller;

import com.projeto.cefom.dto.request.DocumentoCpfRequestDTO;
import com.projeto.cefom.dto.request.InscricaoAtualizarRequestDTO;
import com.projeto.cefom.dto.request.InscricaoCriarRequestDTO;
import com.projeto.cefom.dto.request.InscricaoEncerrarRequestDTO;
import com.projeto.cefom.dto.response.InscricaoCriarResponseDTO;
import com.projeto.cefom.dto.response.InscricaoListarResponseDTO;
import com.projeto.cefom.dto.response.InscricaoResponseDTO;
import com.projeto.cefom.dto.response.StatusCpfInscricaoResponseDTO;
import com.projeto.cefom.service.InscricaoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/adolescentes")
public class InscricaoController {
    private final InscricaoService inscricaoService;

    public InscricaoController(InscricaoService inscricaoService) {
        this.inscricaoService = inscricaoService;
    }

    @PostMapping("/inscricoes/status")
    public ResponseEntity<StatusCpfInscricaoResponseDTO> verificarCpf(@Valid @RequestBody DocumentoCpfRequestDTO dto) {
        StatusCpfInscricaoResponseDTO response = inscricaoService.verificarCpf(dto.cpf());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(value = "/inscricoes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<InscricaoCriarResponseDTO> criar(@Valid @RequestPart("dados") InscricaoCriarRequestDTO dto, @RequestPart(value = "file", required = false) MultipartFile file) {
        InscricaoCriarResponseDTO response = inscricaoService.criar(dto, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(value = "/{idAdolescente}/inscricoes/{idInscricao}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<InscricaoResponseDTO> atualizar(@PathVariable Integer idAdolescente, @PathVariable Integer idInscricao, @Valid @RequestPart("dados") InscricaoAtualizarRequestDTO dto, @RequestPart(value = "file", required = false) MultipartFile file) {
        InscricaoResponseDTO response = inscricaoService.atualizar(idAdolescente, idInscricao, dto, file);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{idAdolescente}/inscricoes/{idInscricao}/encerrar")
    public ResponseEntity<InscricaoResponseDTO> encerrar(@PathVariable Integer idAdolescente, @PathVariable Integer idInscricao, @Valid @RequestBody InscricaoEncerrarRequestDTO dto) {
        InscricaoResponseDTO response = inscricaoService.encerrarPorId(idAdolescente, idInscricao, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idAdolescente}/inscricoes")
    public ResponseEntity<Page<InscricaoListarResponseDTO>> listarPorId(@PathVariable Integer idAdolescente, @PageableDefault(page = 0, size = 10, sort = "dataInicio", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<InscricaoListarResponseDTO> response = inscricaoService.listarPorId(idAdolescente, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idAdolescente}/inscricoes/{idInscricao}")
    public ResponseEntity<InscricaoResponseDTO> buscarPorId(@PathVariable Integer idAdolescente, @PathVariable Integer idInscricao) {
        InscricaoResponseDTO response = inscricaoService.buscarPorId(idAdolescente, idInscricao);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/inscricoes")
    public ResponseEntity<List<InscricaoCriarResponseDTO>> listar() {
        List<InscricaoCriarResponseDTO> response = inscricaoService.listar();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{idAdolescente}/inscricoes/{idInscricao}")
    public ResponseEntity<Void> excluir(@PathVariable Integer idAdolescente, @PathVariable Integer idInscricao) {
        inscricaoService.excluirPorId(idAdolescente, idInscricao);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
