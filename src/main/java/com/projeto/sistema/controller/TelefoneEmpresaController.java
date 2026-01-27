package com.projeto.sistema.controller;

import com.projeto.sistema.dto.request.TelefonesAtualizarRequestDTO;
import com.projeto.sistema.dto.response.TelefoneListarResponseDTO;
import com.projeto.sistema.dto.response.TelefoneResponseDTO;
import com.projeto.sistema.dto.response.TelefonesResponseDTO;
import com.projeto.sistema.service.TelefoneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresas")
public class TelefoneEmpresaController {
    private final TelefoneService telefoneService;

    public TelefoneEmpresaController(TelefoneService telefoneService) {
        this.telefoneService = telefoneService;
    }

    @GetMapping("/{idEmpresa}/telefones")
    public ResponseEntity<List<TelefoneListarResponseDTO>> listarEmpresa(@PathVariable Integer idEmpresa) {
        List<TelefoneListarResponseDTO> response = telefoneService.listarEmpresa(idEmpresa);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{idEmpresa}/telefones/{idTelefone}")
    public ResponseEntity<TelefoneResponseDTO> buscarEmpresaPorId(@PathVariable Integer idEmpresa, @PathVariable Integer idTelefone) {
        TelefoneResponseDTO response = telefoneService.buscarEmpresaPorId(idEmpresa, idTelefone);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{idEmpresa}/telefones/{idTelefone}")
    public ResponseEntity<Void> excluirEmpresaPorId(@PathVariable Integer idEmpresa, @PathVariable Integer idTelefone) {
        telefoneService.excluirEmpresaPorId(idEmpresa, idTelefone);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
