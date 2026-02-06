package com.projeto.cefom.service;

import com.projeto.cefom.enums.TipoResponsabilidade;
import com.projeto.cefom.exceptions.RecursoNaoEncontradoException;
import com.projeto.cefom.exceptions.RegraNegocioException;
import com.projeto.cefom.dto.request.EntrevistaRequestDTO;
import com.projeto.cefom.dto.request.VinculoEntrevistaMatriculaRequestDTO;
import com.projeto.cefom.dto.response.EntrevistaListarResponseDTO;
import com.projeto.cefom.dto.response.EntrevistaResponseDTO;
import com.projeto.cefom.mapper.EntrevistaMapper;
import com.projeto.cefom.model.Empresa;
import com.projeto.cefom.model.Entrevista;
import com.projeto.cefom.repository.EntrevistaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class EntrevistaService {
    private final EntrevistaRepository entrevistaRepository;
    private final EntrevistaMapper entrevistaMapper;
    private final EmpresaService empresaService;
    private final VinculoEntrevistaMatriculaService vinculoEntrevistaMatriculaService;
    private final ResponsavelEmpresaService responsavelEmpresaService;

    public EntrevistaService(EntrevistaRepository entrevistaRepository, EntrevistaMapper entrevistaMapper, EmpresaService empresaService, VinculoEntrevistaMatriculaService vinculoEntrevistaMatriculaService, ResponsavelEmpresaService responsavelEmpresaService) {
        this.entrevistaRepository = entrevistaRepository;
        this.entrevistaMapper = entrevistaMapper;
        this.empresaService = empresaService;
        this.vinculoEntrevistaMatriculaService = vinculoEntrevistaMatriculaService;
        this.responsavelEmpresaService = responsavelEmpresaService;
    }

    @Transactional
    public EntrevistaResponseDTO criar(EntrevistaRequestDTO dto) {
        Empresa empresa = empresaService.buscarEmpresa(dto.idEmpresa());

        if (entrevistaRepository.existsByDataInicioAndEmpresaIdEmpresa(dto.dataEntrevista(), dto.idEmpresa())) {
            throw new RegraNegocioException("Entrevista já cadastrada.");
        }

        Entrevista entrevista = criarEntrevista(dto);

        Entrevista entrevistaSalvo = salvar(entrevista);

        return entrevistaMapper.toResponseDTO(entrevistaSalvo);
    }

    @Transactional
    public EntrevistaResponseDTO atualizar(Integer idEntrevista, EntrevistaRequestDTO dto) {
        Empresa empresa = empresaService.buscarEmpresa(dto.idEmpresa());

        if (entrevistaRepository.existsByDataInicioAndEmpresaIdEmpresaAndIdEntrevistaNot(dto.dataEntrevista(), dto.idEmpresa(), idEntrevista)) {
            throw new RegraNegocioException("Entrevista já cadastrada.");
        }

        Entrevista entrevista = buscarEntrevista(idEntrevista);

        atualizarEntrevista(dto, entrevista);

        Entrevista entrevistaSalvo = salvar(entrevista);

        return entrevistaMapper.toResponseDTO(entrevistaSalvo);
    }

    @Transactional
    public EntrevistaResponseDTO cancelar(Integer idEntrevista) {
        Entrevista entrevista = buscarEntrevista(idEntrevista);
        if (entrevista.getEntrevistaCancelada()) {
            throw new RegraNegocioException("Entrevista já cancelada.");
        }
        entrevista.setEntrevistaCancelada(true);

        Entrevista entrevistaSalvo = salvar(entrevista);

        return entrevistaMapper.toResponseDTO(entrevistaSalvo);
    }

    @Transactional(readOnly = true)
    public List<EntrevistaListarResponseDTO> listar() {
        return entrevistaRepository.findAll()
                .stream()
                .map(entrevistaMapper::toListarResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<EntrevistaListarResponseDTO> listar(Pageable pageable, String nome) {
        if (nome == null || nome.isBlank()) {
            return entrevistaRepository.findAll(pageable)
                    .map(entrevistaMapper::toListarResponseDTO);
        }
        return entrevistaRepository.buscarPorEmpresaApelido(nome, pageable)
                .map(entrevistaMapper::toListarResponseDTO);
    }

    @Transactional(readOnly = true)
    public EntrevistaResponseDTO buscarPorId(Integer idEntrevista) {
        Entrevista entrevista = buscarEntrevista(idEntrevista);
        return entrevistaMapper.toResponseDTO(entrevista);
    }

    @Transactional
    public void excluirPorId(Integer idEntrevista) {
        Entrevista entrevista = buscarEntrevista(idEntrevista);
        entrevistaRepository.deleteById(idEntrevista);
    }

    @Transactional
    public EntrevistaResponseDTO adicionarAdolescente(Integer idEntrevista, VinculoEntrevistaMatriculaRequestDTO dto) {
        Entrevista entrevista = buscarEntrevista(idEntrevista);

        vinculoEntrevistaMatriculaService.adicionarEntrevistaMatricula(entrevista,dto);

        Entrevista entrevistaSalvo = salvar(entrevista);

        return entrevistaMapper.toResponseDTO(entrevistaSalvo);
    }

    @Transactional
    public EntrevistaResponseDTO atualizarAdolescente(Integer idEntrevista, VinculoEntrevistaMatriculaRequestDTO dto) {
        Entrevista entrevista = buscarEntrevista(idEntrevista);

        vinculoEntrevistaMatriculaService.atualizarEntrevistaMatricula(entrevista,dto);

        Entrevista entrevistaSalvo = salvar(entrevista);

        return entrevistaMapper.toResponseDTO(entrevistaSalvo);
    }

    @Transactional
    public EntrevistaResponseDTO removerAdolescente(Integer idEntrevista, Integer idMatricula) {
        Entrevista entrevista = buscarEntrevista(idEntrevista);

        vinculoEntrevistaMatriculaService.removerEntrevistaMatricula(entrevista, idMatricula);

        Entrevista entrevistaSalvo = salvar(entrevista);

        return entrevistaMapper.toResponseDTO(entrevistaSalvo);
    }

    @Transactional(readOnly = true)
    public Entrevista buscarEntrevista(Integer idEntrevista) {
        Entrevista entrevista = entrevistaRepository.findById(idEntrevista)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Entrevista com ID "+idEntrevista+" não encontrada."));
        return entrevista;
    }

    public Entrevista salvar(Entrevista entrevista) {
        Entrevista salvo = entrevistaRepository.save(entrevista);
        return salvo;
    }

    public Entrevista criarEntrevista(EntrevistaRequestDTO dto){
        Entrevista entrevista = new Entrevista();
        entrevista.setDataInicio(dto.dataEntrevista());
        entrevista.setDataFim(dto.dataEntrevista());

        Empresa empresa = empresaService.buscarEmpresa(dto.idEmpresa());

        responsavelEmpresaService.atualizarResponsavelPorResponsabilidade(empresa, TipoResponsabilidade.ENTREVISTAS,dto.responsavelEntevistas(),dto.dataEntrevista());

        empresa.adicionarEntrevista(entrevista);

        return entrevista;
    }

    public void atualizarEntrevista(EntrevistaRequestDTO dto, Entrevista entrevista) {
        entrevista.setDataInicio(dto.dataEntrevista());
        entrevista.setDataFim(dto.dataEntrevista());

        if (!entrevista.getEmpresa().getIdEmpresa().equals(dto.idEmpresa())) {

            Empresa empresaAtual = entrevista.getEmpresa();
            Empresa empresaNova = empresaService.buscarEmpresa(dto.idEmpresa());

            empresaAtual.removerEntrevista(entrevista);
            empresaNova.adicionarEntrevista(entrevista);
        }

        responsavelEmpresaService.atualizarResponsavelPorResponsabilidade(entrevista.getEmpresa(), TipoResponsabilidade.ENTREVISTAS,dto.responsavelEntevistas(),dto.dataEntrevista());
    }
}
