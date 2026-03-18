package com.projeto.cefom.service;

import com.projeto.cefom.dto.response.TerritorioPendenciasResponseDTO;
import com.projeto.cefom.model.Endereco;
import com.projeto.cefom.model.Territorio;
import com.projeto.cefom.repository.EnderecoRepository;
import com.projeto.cefom.utils.TextoUtils;
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

    public void removerTerritorioEnderecos(String bairro) {
        List<Endereco> enderecos = enderecoRepository.findByCidadeAndBairro("LINS", TextoUtils.normalizar(bairro));

        enderecos.forEach(endereco -> {
            Territorio territorio = endereco.getTerritorio();
            if (territorio == null) {
                return;
            }

            territorio.removerEndereco(endereco);
        });
    }

    public void adicionarTerritorioEnderecos(String bairro, Territorio territorio) {
        List<Endereco> enderecos = enderecoRepository.findByCidadeAndBairro("LINS", TextoUtils.normalizar(bairro));

        enderecos.forEach(endereco -> {
            Territorio territorioAtual = endereco.getTerritorio();
            if (territorioAtual != null) {
                territorioAtual.removerEndereco(endereco);
            }
            territorio.adicionarEndereco(endereco);
        });
    }

    public TerritorioPendenciasResponseDTO obterEnderecosSemTerritorio() {
        return new TerritorioPendenciasResponseDTO(
                enderecoRepository.countByTerritorioIsNull(),
                enderecoRepository.listarBairrosSemTerritorio()
        );
    }
}
