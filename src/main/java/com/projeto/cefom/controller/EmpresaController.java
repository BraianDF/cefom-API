package com.projeto.cefom.controller;


import com.projeto.cefom.dto.request.EmpresaRequestDTO;
import com.projeto.cefom.dto.response.EmpresaCriarResponseDTO;
import com.projeto.cefom.dto.response.EmpresaListarEntrevistaResponseDTO;
import com.projeto.cefom.dto.response.EmpresaListarResponseDTO;
import com.projeto.cefom.dto.response.EmpresaSelectResponseDTO;
import com.projeto.cefom.service.EmpresaService;
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
@RequestMapping("/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping
    public ResponseEntity<EmpresaCriarResponseDTO> criar(@Valid @RequestBody EmpresaRequestDTO dto) {
        EmpresaCriarResponseDTO response = empresaService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{idEmpresa}")
    public ResponseEntity<EmpresaCriarResponseDTO> atualizar(@PathVariable Integer idEmpresa, @Valid @RequestBody EmpresaRequestDTO dto) {
        EmpresaCriarResponseDTO response = empresaService.atualizar(idEmpresa, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<EmpresaListarResponseDTO>> listar(@PageableDefault(page = 0, size = 10, sort = "apelido", direction = Sort.Direction.ASC) Pageable pageable, @RequestParam(defaultValue = "") String nome) {
        Page<EmpresaListarResponseDTO> response = empresaService.listar(pageable, nome);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/select")
    public ResponseEntity<List<EmpresaSelectResponseDTO>> listarSelect() {
        List<EmpresaSelectResponseDTO> response = empresaService.listarSelect();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //Verificar a necessidade do endpoint
    @GetMapping("/entrevistas")
    public ResponseEntity<List<EmpresaListarEntrevistaResponseDTO>> listarEntrevistas() {
        List<EmpresaListarEntrevistaResponseDTO> response = empresaService.listarEntrevistas();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idEmpresa}")
    public ResponseEntity<EmpresaCriarResponseDTO> buscarPorId(@PathVariable Integer idEmpresa) {
        EmpresaCriarResponseDTO response = empresaService.buscarPorId(idEmpresa);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{idEmpresa}")
    public  ResponseEntity<Void> excluirPorId(@PathVariable Integer idEmpresa) {
        empresaService.excluirPorId(idEmpresa);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
