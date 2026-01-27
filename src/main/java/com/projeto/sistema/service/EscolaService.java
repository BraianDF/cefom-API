package com.projeto.sistema.service;

import com.projeto.sistema.dto.request.EscolaRequestDTO;
import com.projeto.sistema.dto.response.EscolaResponseDTO;
import com.projeto.sistema.dto.response.EscolaListarResponseDTO;
import com.projeto.sistema.exceptions.RecursoNaoEncontradoException;
import com.projeto.sistema.exceptions.RegraNegocioException;
import com.projeto.sistema.mapper.EscolaMapper;
import com.projeto.sistema.model.Escola;
import com.projeto.sistema.repository.EscolaRepository;
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

        if (escolaRepository.existsByNome(dto.nome())) {
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

        if (escolaRepository.existsByNomeAndIdEscolaNot(dto.nome(), idEscola)) {
            throw new RegraNegocioException("Já existe outra escola com esse nome.");
        }

        escola.setNome(dto.nome());
        escola.setTipo(dto.tipo());

        enderecoService.atualizarEndereco(dto.endereco(),escola,dataModificacao);

        Escola escolaAtualizada = salvar(escola);

        return escolaMapper.toResponseDTO(escolaAtualizada, dataModificacao);
    }

    @Transactional(readOnly = true)
    public List<EscolaListarResponseDTO> listar() {
        return escolaRepository.findAll()
                .stream()
                .map(escolaMapper::toListarResponseDTO)
                .toList();
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
