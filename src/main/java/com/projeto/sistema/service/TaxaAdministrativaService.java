package com.projeto.sistema.service;

import com.projeto.sistema.exceptions.RecursoNaoEncontradoException;
import com.projeto.sistema.exceptions.RegraNegocioException;
import com.projeto.sistema.dto.response.TaxaAdministrativaListarResponseDTO;
import com.projeto.sistema.dto.response.TaxaAdministrativaResponseDTO;
import com.projeto.sistema.mapper.TaxaAdministrativaMapper;
import com.projeto.sistema.model.Empresa;
import com.projeto.sistema.model.TaxaAdministrativa;
import com.projeto.sistema.repository.EmpresaRepository;
import com.projeto.sistema.repository.TaxaAdministrativaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class TaxaAdministrativaService {
    private final TaxaAdministrativaRepository taxaAdministrativaRepository;
    private final TaxaAdministrativaMapper taxaAdministrativaMapper;
    private final EmpresaRepository empresaRepository;

    public TaxaAdministrativaService(TaxaAdministrativaRepository taxaAdministrativaRepository, TaxaAdministrativaMapper taxaAdministrativaMapper, EmpresaRepository empresaRepository) {
        this.taxaAdministrativaRepository = taxaAdministrativaRepository;
        this.taxaAdministrativaMapper = taxaAdministrativaMapper;
        this.empresaRepository = empresaRepository;
    }

    public void atualizarTaxa(BigDecimal valorTaxa, Empresa empresa, LocalDate data) {
        TaxaAdministrativa taxaAtual = empresa.getTaxasAdministrativas().stream()
                .filter(e -> e.getDataFim() == null)
                .max(Comparator.comparing(TaxaAdministrativa::getDataInicio))
                .orElse(null);

        if (taxaAtual != null && taxaAtual.getValorTaxa() == valorTaxa) {
            // Não mudou → não faz nada
            return;
        }

        // Encerra atual (se existir)
        if (taxaAtual != null) {
            taxaAtual.setDataFim(data);
        }

        // Cria novo
        criarTaxa(valorTaxa, empresa, data);
    }

    public void criarTaxa(BigDecimal valorTaxa, Empresa empresa, LocalDate data) {
        TaxaAdministrativa taxa = new TaxaAdministrativa();
        taxa.setValorTaxa(valorTaxa);
        taxa.setDataInicio(data);

        empresa.adicionarTaxaAdministrativa(taxa);
    }

    private Empresa buscarEmpresa(Integer idEmpresa) {
        Empresa empresa = empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Empresa com ID "+idEmpresa+" não encontrada."));
        return empresa;
    }

    private TaxaAdministrativa buscarTaxaEmpresa(Integer idEmpresa, Integer idTaxa) {
        Empresa empresa = buscarEmpresa(idEmpresa);

        TaxaAdministrativa taxa = taxaAdministrativaRepository.findById(idTaxa)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Taxa Administrativa com ID "+idTaxa+" não encontrada."));

        if (taxa.getEmpresa() == null) {
            throw new RegraNegocioException("Taxa Administrativa não pertence a uma empresa.");
        }

        if (!taxa.getEmpresa().getIdEmpresa().equals(idEmpresa)) {
            throw new RegraNegocioException("Taxa Administrativa não pertence a empresa.");
        }

        return taxa;
    }


    @Transactional(readOnly = true)
    public Page<TaxaAdministrativaListarResponseDTO> listarEmpresa(Integer idEmpresa, Pageable pageable) {
        //Verifica se a empresa existe e retorna ela
        Empresa empresa = buscarEmpresa(idEmpresa);

        //Retorna todos que a empresa tiver
        return taxaAdministrativaRepository.findByEmpresaIdEmpresaOrderByDataInicioDesc(idEmpresa, pageable)
                .map(taxaAdministrativaMapper::toListarResponseDTO);
    }

    @Transactional(readOnly = true)
    public TaxaAdministrativaResponseDTO buscarEmpresaPorId(Integer idEmpresa, Integer idTaxa) {
        TaxaAdministrativa taxa = buscarTaxaEmpresa(idEmpresa, idTaxa);
        return taxaAdministrativaMapper.toResponseDTO(taxa);
    }

    @Transactional
    public void excluirEmpresaPorId(Integer idEmpresa, Integer idTaxa) {
        TaxaAdministrativa taxa = buscarTaxaEmpresa(idEmpresa, idTaxa);
        taxaAdministrativaRepository.deleteById(idTaxa);
    }
}
