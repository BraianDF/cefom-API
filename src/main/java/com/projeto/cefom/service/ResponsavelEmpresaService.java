package com.projeto.cefom.service;

import com.projeto.cefom.exceptions.RecursoNaoEncontradoException;
import com.projeto.cefom.exceptions.RegraNegocioException;
import com.projeto.cefom.dto.request.ResponsavelEmpresaRequestDTO;
import com.projeto.cefom.dto.response.ResponsavelEmpresaListarResponseDTO;
import com.projeto.cefom.dto.response.ResponsavelEmpresaResponseDTO;
import com.projeto.cefom.enums.TipoResponsabilidade;
import com.projeto.cefom.mapper.ResponsavelEmpresaMapper;
import com.projeto.cefom.model.Empresa;
import com.projeto.cefom.model.ResponsavelEmpresa;
import com.projeto.cefom.repository.EmpresaRepository;
import com.projeto.cefom.repository.ResponsavelEmpresaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;

@Service
public class ResponsavelEmpresaService {

    private final ResponsavelEmpresaRepository responsavelEmpresaRepository;
    private final ResponsavelEmpresaMapper responsavelEmpresaMapper;
    private final EmpresaRepository empresaRepository;

    public ResponsavelEmpresaService(ResponsavelEmpresaRepository responsavelEmpresaRepository, ResponsavelEmpresaMapper responsavelEmpresaMapper, EmpresaRepository empresaRepository) {
        this.responsavelEmpresaRepository = responsavelEmpresaRepository;
        this.responsavelEmpresaMapper = responsavelEmpresaMapper;
        this.empresaRepository = empresaRepository;
    }

    public void atualizarResponsaveis(ResponsavelEmpresaRequestDTO dto, Empresa empresa, LocalDate data) {
        atualizarResponsavelPorResponsabilidade(empresa, TipoResponsabilidade.EMPRESA, dto.responsavelEmpresa(), data);

        if (dto.responsavelAprendizes() != null && !dto.responsavelAprendizes().isBlank()) {
            atualizarResponsavelPorResponsabilidade(empresa, TipoResponsabilidade.APRENDIZES, dto.responsavelAprendizes(), data);
        } else {
            atualizarResponsavelPorResponsabilidade(empresa, TipoResponsabilidade.APRENDIZES, dto.responsavelEmpresa(), data);
        }

        if (dto.responsavelEntevistas() != null && !dto.responsavelEntevistas().isBlank()) {
            atualizarResponsavelPorResponsabilidade(empresa, TipoResponsabilidade.ENTREVISTAS, dto.responsavelEntevistas(), data);
        } else if (dto.responsavelAprendizes() != null && !dto.responsavelAprendizes().isBlank()) {
            atualizarResponsavelPorResponsabilidade(empresa, TipoResponsabilidade.ENTREVISTAS, dto.responsavelAprendizes(), data);
        } else {
            atualizarResponsavelPorResponsabilidade(empresa, TipoResponsabilidade.ENTREVISTAS, dto.responsavelEmpresa(), data);
        }
    }

    public void atualizarResponsavelPorResponsabilidade(Empresa empresa, TipoResponsabilidade responsabilidade, String nome, LocalDate data) {
        ResponsavelEmpresa responsavelAtual = empresa.getResponsaveis().stream()
                .filter(t -> t.getResponsabilidade() == responsabilidade)
                .filter(t -> t.getDataFim() == null)
                .max(Comparator.comparing(ResponsavelEmpresa::getDataInicio))
                .orElse(null);

        // Se não mudou, não faz nada
        if (responsavelAtual != null &&
                responsavelAtual.getNome().equals(nome)) {
            return;
        }

        // Encerra atual
        if (responsavelAtual != null) {
            responsavelAtual.setDataFim(data);
        }

        // Cria novo
        criarResponsavel(nome, responsabilidade, empresa, data);
    }


    public void criarResponsaveis(ResponsavelEmpresaRequestDTO dto, Empresa empresa, LocalDate data) {
        criarResponsavel(dto.responsavelEmpresa(), TipoResponsabilidade.EMPRESA, empresa, data);

        if (dto.responsavelAprendizes() != null && !dto.responsavelAprendizes().isBlank()) {
            criarResponsavel(dto.responsavelAprendizes(), TipoResponsabilidade.APRENDIZES, empresa, data);
        } else {
            criarResponsavel(dto.responsavelEmpresa(), TipoResponsabilidade.APRENDIZES, empresa, data);
        }

        if (dto.responsavelEntevistas() != null && !dto.responsavelEntevistas().isBlank()) {
            criarResponsavel(dto.responsavelEntevistas(), TipoResponsabilidade.ENTREVISTAS, empresa, data);
        } else if (dto.responsavelAprendizes() != null && !dto.responsavelAprendizes().isBlank()) {
            criarResponsavel(dto.responsavelAprendizes(), TipoResponsabilidade.ENTREVISTAS, empresa, data);
        } else {
            criarResponsavel(dto.responsavelEmpresa(), TipoResponsabilidade.ENTREVISTAS, empresa, data);
        }
    }

    public void criarResponsavel(String nomeResponsavel, TipoResponsabilidade responsabilidade, Empresa empresa, LocalDate data) {
        ResponsavelEmpresa responsavel = new ResponsavelEmpresa();
        responsavel.setNome(nomeResponsavel);
        responsavel.setResponsabilidade(responsabilidade);
        responsavel.setDataInicio(data);

        empresa.adicionarResponsavel(responsavel);

        if(responsavel.getIdResponsavelEmpresa() == null) {
            salvar(responsavel);
        }
    }

    private Empresa buscarEmpresa(Integer idEmpresa) {
        Empresa empresa = empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Empresa com ID "+idEmpresa+" não encontrada."));
        return empresa;
    }

    private ResponsavelEmpresa buscarResponsavelEmpresa(Integer idEmpresa, Integer idResponsavel) {
        Empresa empresa = buscarEmpresa(idEmpresa);

        ResponsavelEmpresa responsavel = responsavelEmpresaRepository.findById(idResponsavel)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Responsavel com ID "+idResponsavel+" não encontrado."));

        if (responsavel.getEmpresa() == null) {
            throw new RegraNegocioException("Responsavel não pertence a uma empresa.");
        }

        if (!responsavel.getEmpresa().getIdEmpresa().equals(idEmpresa)) {
            throw new RegraNegocioException("Responsavel não pertence a empresa.");
        }

        return responsavel;
    }

    private ResponsavelEmpresa salvar(ResponsavelEmpresa responsavel) {
        return responsavelEmpresaRepository.save(responsavel);
    }

    @Transactional(readOnly = true)
    public Page<ResponsavelEmpresaListarResponseDTO> listarEmpresa(Integer idEmpresa, Pageable pageable) {
        //Verifica se a empresa existe e retorna ela
        Empresa empresa = buscarEmpresa(idEmpresa);

        //Retorna todos que a empresa tiver
        return responsavelEmpresaRepository.findByEmpresaIdEmpresaOrderByDataInicioDesc(idEmpresa, pageable)
                .map(responsavelEmpresaMapper::toListarResponseDTO);
    }

    @Transactional(readOnly = true)
    public ResponsavelEmpresaResponseDTO buscarEmpresaPorId(Integer idEmpresa, Integer idResponsavel) {
        ResponsavelEmpresa responsavel = buscarResponsavelEmpresa(idEmpresa, idResponsavel);
        return responsavelEmpresaMapper.toResponseDTO(responsavel);
    }

    @Transactional
    public void excluirEmpresaPorId(Integer idEmpresa, Integer idResponsavel) {
        ResponsavelEmpresa responsavel = buscarResponsavelEmpresa(idEmpresa, idResponsavel);
        responsavelEmpresaRepository.deleteById(idResponsavel);
    }


}
