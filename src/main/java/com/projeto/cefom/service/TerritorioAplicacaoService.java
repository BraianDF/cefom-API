package com.projeto.cefom.service;

import com.projeto.cefom.model.Endereco;
import com.projeto.cefom.model.Territorio;
import com.projeto.cefom.repository.EnderecoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TerritorioAplicacaoService {
    private final EnderecoRepository enderecoRepository;

    public TerritorioAplicacaoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    private String normalizar(String valor) {
        return valor == null ? null : valor.trim().toUpperCase();
    }

    public void removerTerritorioEnderecos(String bairro) {
        List<Endereco> enderecos = enderecoRepository.findByCidadeAndBairro("LINS", normalizar(bairro));

        enderecos.forEach(endereco -> {
            Territorio territorio = endereco.getTerritorio();
            if (territorio == null) {
                return;
            }

            territorio.removerEndereco(endereco);
        });
    }

    public void adicionarTerritorioEnderecos(String bairro, Territorio territorio) {
        List<Endereco> enderecos = enderecoRepository.findByCidadeAndBairro("LINS", normalizar(bairro));

        enderecos.forEach(endereco -> {
            Territorio territorioAtual = endereco.getTerritorio();
            if (territorioAtual != null) {
                territorioAtual.removerEndereco(endereco);
            }
            territorio.adicionarEndereco(endereco);
        });
    }
}
