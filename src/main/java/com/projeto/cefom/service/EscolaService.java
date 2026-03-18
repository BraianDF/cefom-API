package com.projeto.cefom.service;

import com.projeto.cefom.dto.request.EscolaRequestDTO;
import com.projeto.cefom.dto.response.EscolaResponseDTO;
import com.projeto.cefom.dto.response.EscolaListarResponseDTO;
import com.projeto.cefom.dto.response.EscolaSelectResponseDTO;
import com.projeto.cefom.exceptions.RecursoNaoEncontradoException;
import com.projeto.cefom.exceptions.RegraNegocioException;
import com.projeto.cefom.mapper.EscolaMapper;
import com.projeto.cefom.model.Escola;
import com.projeto.cefom.repository.EscolaRepository;
import com.projeto.cefom.utils.TextoUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EscolaService {
    private final EscolaRepository escolaRepository;
    private final EnderecoService enderecoService;
    private final EscolaMapper escolaMapper;

    public EscolaService(EscolaRepository escolaRepository, EnderecoService enderecoService, EscolaMapper escolaMapper) {
        this.escolaRepository = escolaRepository;
        this.enderecoService = enderecoService;
        this.escolaMapper = escolaMapper;
    }

    @Transactional
    public EscolaResponseDTO criar(EscolaRequestDTO dto) {
        LocalDate dataCriacao = dto.dataModificacao();
        if(dataCriacao == null) {
            dataCriacao = LocalDate.now();
        }

        if (escolaRepository.existsByNome(TextoUtils.normalizar(dto.nome()))) {
            throw new RegraNegocioException("Escola já cadastrada.");
        }

        Escola escola = new Escola();
        escola.setNome(dto.nome());
        escola.setTipo(dto.tipo());

        if (escola.getIdEscola() == null) {
            escola = salvar(escola);
        }

        enderecoService.criarEndereco(escola,dataCriacao,dto.endereco().cep(),dto.endereco().logradouro(),dto.endereco().numero(),dto.endereco().complemento(),dto.endereco().bairro(),dto.endereco().cidade(),dto.endereco().estado());

        Escola escolaSalva = salvar(escola);

        return escolaMapper.toResponseDTO(escolaSalva, dataCriacao);

    }

    @Transactional
    public EscolaResponseDTO atualizar(Integer idEscola, EscolaRequestDTO dto) {
        LocalDate dataModificacao = dto.dataModificacao();
        if(dataModificacao == null) {
            dataModificacao = LocalDate.now();
        }

        Escola escola = buscarEscola(idEscola);

        if (escolaRepository.existsByNomeAndIdEscolaNot(TextoUtils.normalizar(dto.nome()), idEscola)) {
            throw new RegraNegocioException("Já existe outra escola com esse nome.");
        }

        escola.setNome(dto.nome());
        escola.setTipo(dto.tipo());

        enderecoService.atualizarEndereco(dto.endereco(),escola,dataModificacao);

        Escola escolaAtualizada = salvar(escola);

        return escolaMapper.toResponseDTO(escolaAtualizada, dataModificacao);
    }

    @Transactional(readOnly = true)
    public List<EscolaSelectResponseDTO> listarSelect() {
        return escolaRepository.findAll()
                .stream()
                .map(escolaMapper::toSelectResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<EscolaListarResponseDTO> listar(Pageable pageable, String nome) {
        if (nome == null || nome.isBlank()) {
            return escolaRepository.findAll(pageable)
                    .map(escolaMapper::toListarResponseDTO);
        }
        return escolaRepository.findByNomeContainingIgnoreCase(TextoUtils.normalizar(nome), pageable)
                .map(escolaMapper::toListarResponseDTO);
    }

    @Transactional(readOnly = true)
    public EscolaResponseDTO buscarPorId(Integer idEscola) {
        Escola escola = buscarEscola(idEscola);
        return escolaMapper.toResponseDTO(escola, LocalDate.now());
    }

    @Transactional
    public void excluirPorId(Integer idEscola) {
        Escola escola = buscarEscola(idEscola);
        escolaRepository.deleteById(idEscola);
    }

    public Escola salvar(Escola escola) {
        Escola salvo = escolaRepository.save(escola);
        return salvo;
    }

    @Transactional(readOnly = true)
    public Escola buscarEscola (Integer idEscola) {
        Escola escola = escolaRepository.findById(idEscola)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Escola com ID "+idEscola+" não encontrada."));
        return escola;
    }

}
