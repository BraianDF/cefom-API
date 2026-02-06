package com.projeto.cefom.service;


import com.projeto.cefom.dto.request.EnderecoAdolescenteAtualizarRequestDTO;
import com.projeto.cefom.dto.request.EnderecoMatriculaRequestDTO;
import com.projeto.cefom.dto.request.EnderecoRequestDTO;
import com.projeto.cefom.dto.response.EnderecoListarResponseDTO;
import com.projeto.cefom.dto.response.EnderecoMatriculaResponseDTO;
import com.projeto.cefom.dto.response.EnderecoResponseDTO;
import com.projeto.cefom.enums.TipoResidencia;
import com.projeto.cefom.enums.Estado;
import com.projeto.cefom.exceptions.RecursoNaoEncontradoException;
import com.projeto.cefom.exceptions.RegraNegocioException;
import com.projeto.cefom.mapper.EnderecoMapper;
import com.projeto.cefom.model.Adolescente;
import com.projeto.cefom.model.Endereco;
import com.projeto.cefom.model.Escola;
import com.projeto.cefom.model.Territorio;
import com.projeto.cefom.model.Empresa;
import com.projeto.cefom.repository.EmpresaRepository;
import com.projeto.cefom.repository.EnderecoRepository;
import com.projeto.cefom.repository.EscolaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final TerritorioService territorioService;
    private final EnderecoMapper enderecoMapper;
    private final AdolescenteService adolescenteService;
    private final EscolaRepository escolaRepository;
    private final EmpresaRepository empresaRepository;

    public EnderecoService(EnderecoRepository enderecoRepository, TerritorioService territorioService, EnderecoMapper enderecoMapper, AdolescenteService adolescenteService, EscolaRepository escolaRepository, EmpresaRepository empresaRepository) {
        this.enderecoRepository = enderecoRepository;
        this.territorioService = territorioService;
        this.enderecoMapper = enderecoMapper;
        this.adolescenteService = adolescenteService;
        this.escolaRepository = escolaRepository;
        this.empresaRepository = empresaRepository;
    }

    public void atualizarEndereco(EnderecoRequestDTO dto, Adolescente adolescente, LocalDate data) {

        Endereco enderecoAtual = adolescente.getEnderecos().stream()
                .filter(e -> e.getDataFim() == null)
                .max(Comparator.comparing(Endereco::getDataInicio))
                .orElse(null);

        if (enderecoAtual != null && enderecoIgual(enderecoAtual,dto.cep(), dto.logradouro(), dto.numero(), dto.complemento(), dto.bairro(), dto.cidade(), dto.estado())) {
            // Endereço não mudou → não faz nada
            return;
        }

        // Encerra endereço atual (se existir)
        if (enderecoAtual != null) {
            enderecoAtual.setDataFim(data);
        }

        // Cria novo endereço
        criarEndereco(adolescente, data, dto.cep(), dto.logradouro(), dto.numero(), dto.complemento(), dto.bairro(), dto.cidade(), dto.estado(), null);
    }

    public void atualizarEnderecoMatricula(EnderecoMatriculaRequestDTO dto, Adolescente adolescente, LocalDate data) {

        Endereco enderecoAtual = adolescente.getEnderecos().stream()
                .filter(e -> e.getDataFim() == null)
                .max(Comparator.comparing(Endereco::getDataInicio))
                .orElse(null);

        if (enderecoAtual != null && enderecoIgual(enderecoAtual,dto.cep(), dto.logradouro(), dto.numero(), dto.complemento(), dto.bairro(), dto.cidade(), dto.estado())) {
            // Endereço não mudou

            if(dto.tipoResidencia() != null &&
                    !Objects.equals(enderecoAtual.getTipoResidencia(), dto.tipoResidencia())) {

                enderecoAtual.setTipoResidencia(dto.tipoResidencia());
            }

            return;
        }

        // Encerra endereço atual (se existir)
        if (enderecoAtual != null) {
            enderecoAtual.setDataFim(data);
        }

        // Cria novo endereço
        criarEndereco(adolescente, data, dto.cep(), dto.logradouro(), dto.numero(), dto.complemento(), dto.bairro(), dto.cidade(), dto.estado(), dto.tipoResidencia());
    }

    public void atualizarEndereco(EnderecoRequestDTO dto, Escola escola, LocalDate data) {

        Endereco enderecoAtual = escola.getEnderecos().stream()
                .filter(e -> e.getDataFim() == null)
                .max(Comparator.comparing(Endereco::getDataInicio))
                .orElse(null);

        if (enderecoAtual != null && enderecoIgual(enderecoAtual,dto.cep(), dto.logradouro(), dto.numero(), dto.complemento(), dto.bairro(), dto.cidade(), dto.estado())) {
            // Endereço não mudou → não faz nada
            return;
        }

        // Encerra endereço atual (se existir)
        if (enderecoAtual != null) {
            enderecoAtual.setDataFim(data);
        }

        // Cria novo endereço
        criarEndereco(escola, data, dto.cep(), dto.logradouro(), dto.numero(), dto.complemento(), dto.bairro(), dto.cidade(), dto.estado());
    }

    public void atualizarEndereco(EnderecoRequestDTO dto, Empresa empresa, LocalDate data) {

        Endereco enderecoAtual = empresa.getEnderecos().stream()
                .filter(e -> e.getDataFim() == null)
                .max(Comparator.comparing(Endereco::getDataInicio))
                .orElse(null);

        if (enderecoAtual != null && enderecoIgual(enderecoAtual,dto.cep(), dto.logradouro(), dto.numero(), dto.complemento(), dto.bairro(), dto.cidade(), dto.estado())) {
            // Endereço não mudou → não faz nada
            return;
        }

        // Encerra endereço atual (se existir)
        if (enderecoAtual != null) {
            enderecoAtual.setDataFim(data);
        }

        // Cria novo endereço
        criarEndereco(empresa, data, dto.cep(), dto.logradouro(), dto.numero(), dto.complemento(), dto.bairro(), dto.cidade(), dto.estado());
    }

    public void criarEndereco(Adolescente adolescente, LocalDate data, String cep, String logradouro, String numero, String complemento, String bairro, String cidade, Estado estado, TipoResidencia tipoResidencia) {
        Endereco endereco = new Endereco();
        endereco.setCep(limparCep(cep));
        endereco.setLogradouro(logradouro);
        endereco.setNumero(numero);
        endereco.setComplemento(complemento);
        endereco.setBairro(bairro);
        endereco.setCidade(cidade);
        endereco.setEstado(estado);
        endereco.setTipoResidencia(tipoResidencia);
        endereco.setDataInicio(data);

        adicionarTerritorio(endereco);

        adolescente.adicionarEndereco(endereco);
    }

    public void criarEndereco(Escola escola, LocalDate data, String cep, String logradouro, String numero, String complemento, String bairro, String cidade, Estado estado) {
        Endereco endereco = new Endereco();
        endereco.setCep(limparCep(cep));
        endereco.setLogradouro(logradouro);
        endereco.setNumero(numero);
        endereco.setComplemento(complemento);
        endereco.setBairro(bairro);
        endereco.setCidade(cidade);
        endereco.setEstado(estado);
        endereco.setDataInicio(data);

        adicionarTerritorio(endereco);

        escola.adicionarEndereco(endereco);
    }

    public void criarEndereco(Empresa empresa, LocalDate data, String cep, String logradouro, String numero, String complemento, String bairro, String cidade, Estado estado) {
        Endereco endereco = new Endereco();
        endereco.setCep(limparCep(cep));
        endereco.setLogradouro(logradouro);
        endereco.setNumero(numero);
        endereco.setComplemento(complemento);
        endereco.setBairro(bairro);
        endereco.setCidade(cidade);
        endereco.setEstado(estado);
        endereco.setDataInicio(data);

        adicionarTerritorio(endereco);

        empresa.adicionarEndereco(endereco);
    }

    public void adicionarTerritorio(Endereco endereco) {
        if (endereco == null) {
            throw new RegraNegocioException("Endereço não pode ser nulo.");
        }

        if (endereco.getCidade() == null || endereco.getCidade().isBlank()) {
            throw new RegraNegocioException("Cidade é obrigatória para definir o território.");
        }

        String cidade = normalizar(endereco.getCidade());

        Territorio territorio;

        if("LINS".equals(cidade)){
            if (endereco.getBairro() == null || endereco.getBairro().isBlank()) {
                throw new RegraNegocioException("Bairro é obrigatório para endereços da cidade de Lins.");
            }
            String bairro = normalizar(endereco.getBairro());
            territorio = territorioService.buscarPorBairro(bairro);
        } else {
            territorio = territorioService.buscarOuCriarPorResultado("Fora de Lins");
        }

        territorio.adicionarEndereco(endereco);
    }

    public boolean enderecoIgual(Endereco e, String cep, String logradouro, String numero, String complemento, String bairro, String cidade, Estado estado) {
        return e.getCep().equals(limparCep(cep)) &&
                Objects.equals(e.getLogradouro(), logradouro) &&
                Objects.equals(e.getNumero(), numero) &&
                Objects.equals(e.getComplemento(), complemento) &&
                Objects.equals(e.getBairro(), bairro) &&
                Objects.equals(e.getCidade(), cidade) &&
                Objects.equals(e.getEstado(), estado);
    }

    private String normalizar(String valor) {
        return valor == null ? null : valor.trim().toUpperCase();
    }

    @Transactional
    public EnderecoMatriculaResponseDTO atualizarAdolescente(Integer idAdolescente, EnderecoAdolescenteAtualizarRequestDTO dto) {
        LocalDate dataModificacao = dto.dataModificacao();
        if(dataModificacao == null) {
            dataModificacao = LocalDate.now();
        }

        Adolescente adolescente = adolescenteService.buscarAdolescente(idAdolescente);

        atualizarEnderecoMatricula(dto.endereco(),adolescente,dataModificacao);

        Adolescente adolescenteSalvo = adolescenteService.salvar(adolescente);
        return enderecoMapper.toResponseDTO(adolescenteSalvo, dataModificacao);
    }

    @Transactional(readOnly = true)
    public Page<EnderecoListarResponseDTO> listarAdolescente(Integer idAdolescente, Pageable pageable) {
        Adolescente adolescente = adolescenteService.buscarAdolescente(idAdolescente);

        return enderecoRepository.findByAdolescenteIdAdolescenteOrderByDataInicioDesc(idAdolescente, pageable)
                .map(enderecoMapper::toListarResponseDTO);
    }

    @Transactional(readOnly = true)
    public EnderecoMatriculaResponseDTO buscarAdolescentePorId(Integer idAdolescente, Integer idEndereco) {
        Endereco endereco = buscarEnderecoAdolescente(idAdolescente, idEndereco);
        return enderecoMapper.toAdolescenteResponseDTO(endereco);
    }

    @Transactional
    public void excluirAdolescentePorId(Integer idAdolescente, Integer idEndereco) {
        Endereco endereco = buscarEnderecoAdolescente(idAdolescente, idEndereco);
        enderecoRepository.deleteById(idEndereco);
    }

    @Transactional(readOnly = true)
    public Page<EnderecoListarResponseDTO> listarEscola(Integer idEscola, Pageable pageable) {
        Escola escola = buscarEscola(idEscola);
        return enderecoRepository.findByEscolaIdEscolaOrderByDataInicioDesc(idEscola, pageable)
                .map(enderecoMapper::toListarResponseDTO);
    }

    @Transactional(readOnly = true)
    public EnderecoResponseDTO buscarEscolaPorId(Integer idEscola, Integer idEndereco) {
        Endereco endereco = buscarEnderecoEscola(idEscola, idEndereco);
        return enderecoMapper.toResponseDTO(endereco);
    }

    @Transactional
    public void excluirEscolaPorId(Integer idEscola, Integer idEndereco) {
        Endereco endereco = buscarEnderecoEscola(idEscola, idEndereco);
        enderecoRepository.deleteById(idEndereco);
    }

    @Transactional(readOnly = true)
    public Page<EnderecoListarResponseDTO> listarEmpresa(Integer idEmpresa, Pageable pageable) {
        Empresa empresa = buscarEmpresa(idEmpresa);
        return enderecoRepository.findByEmpresaIdEmpresaOrderByDataInicioDesc(idEmpresa, pageable)
                .map(enderecoMapper::toListarResponseDTO);
    }

    @Transactional(readOnly = true)
    public EnderecoResponseDTO buscarEmpresaPorId(Integer idEmpresa, Integer idEndereco) {
        Endereco endereco = buscarEnderecoEmpresa(idEmpresa, idEndereco);
        return enderecoMapper.toResponseDTO(endereco);
    }

    @Transactional
    public void excluirEmpresaPorId(Integer idEmpresa, Integer idEndereco) {
        Endereco endereco = buscarEnderecoEmpresa(idEmpresa, idEndereco);
        enderecoRepository.deleteById(idEndereco);
    }

    private Endereco buscarEnderecoAdolescente(Integer idAdolescente, Integer idEndereco) {
        Adolescente adolescente = adolescenteService.buscarAdolescente(idAdolescente);

        Endereco endereco = enderecoRepository.findById(idEndereco)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Endereço com ID "+idEndereco+" não encontrado."));

        if (endereco.getAdolescente() == null) {
            throw new RegraNegocioException("Endereço não pertence a um adolescente.");
        }

        if (!endereco.getAdolescente().getIdAdolescente().equals(idAdolescente)) {
            throw new RegraNegocioException("Endereço não pertence ao adolescente.");
        }

        return endereco;
    }

    private Endereco buscarEnderecoEscola(Integer idEscola, Integer idEndereco) {
        Escola escola = buscarEscola(idEscola);

        Endereco endereco = enderecoRepository.findById(idEndereco)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Endereço com ID "+idEndereco+" não encontrado."));

        if (endereco.getEscola() == null) {
            throw new RegraNegocioException("Endereço não pertence a uma escola.");
        }

        if (!endereco.getEscola().getIdEscola().equals(idEscola)) {
            throw new RegraNegocioException("Endereço não pertence a escola.");
        }

        return endereco;
    }

    private Escola buscarEscola(Integer idEscola) {
        Escola escola = escolaRepository.findById(idEscola)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Escola com ID "+idEscola+" não encontrada."));
        return escola;
    }

    private Empresa buscarEmpresa(Integer idEmpresa) {
        Empresa empresa = empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Empresa com ID "+idEmpresa+" não encontrada."));
        return empresa;
    }

    private Endereco buscarEnderecoEmpresa(Integer idEmpresa, Integer idEndereco) {
        Empresa empresa = buscarEmpresa(idEmpresa);

        Endereco endereco = enderecoRepository.findById(idEndereco)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Endereço com ID "+idEndereco+" não encontrado."));

        if (endereco.getEmpresa() == null) {
            throw new RegraNegocioException("Endereço não pertence a uma empresa.");
        }

        if (!endereco.getEmpresa().getIdEmpresa().equals(idEmpresa)) {
            throw new RegraNegocioException("Endereço não pertence a empresa.");
        }

        return endereco;
    }

    public String limparCep(String cep) {
        if (cep == null || cep.isBlank()) {
            throw new RegraNegocioException("CEP não pode ser vazio.");
        }
        return cep.replaceAll("\\D", "");
    }

}
