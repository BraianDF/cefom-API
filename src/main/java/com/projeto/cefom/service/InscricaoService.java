package com.projeto.cefom.service;

import com.projeto.cefom.dto.request.InscricaoAtualizarRequestDTO;
import com.projeto.cefom.dto.request.InscricaoCriarRequestDTO;
import com.projeto.cefom.dto.request.InscricaoEncerrarRequestDTO;
import com.projeto.cefom.dto.response.InscricaoCriarResponseDTO;
import com.projeto.cefom.dto.response.InscricaoListarResponseDTO;
import com.projeto.cefom.dto.response.InscricaoResponseDTO;
import com.projeto.cefom.dto.response.StatusCpfInscricaoResponseDTO;
import com.projeto.cefom.enums.StatusCpf;
import com.projeto.cefom.exceptions.RecursoNaoEncontradoException;
import com.projeto.cefom.exceptions.RegraNegocioException;
import com.projeto.cefom.image.FotoAdolescenteService;
import com.projeto.cefom.mapper.InscricaoMapper;
import com.projeto.cefom.model.*;
import com.projeto.cefom.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;

@Service
public class InscricaoService {

    private final InscricaoRepository inscricaoRepository;
    private final DocumentoService documentoService;
    private final AdolescenteService adolescenteService;
    private final TelefoneService telefoneService;
    private final EnderecoService enderecoService;
    private final EscolaridadeService escolaridadeService;
    private final InscricaoMapper inscricaoMapper;
    private final SequenceNumInscricaoRepository sequenceNumInscricaoRepository;
    private final FotoAdolescenteService fotoAdolescenteService;

    public InscricaoService(InscricaoRepository inscricaoRepository, DocumentoService documentoService, AdolescenteService adolescenteService, TelefoneService telefoneService, EnderecoService enderecoService, EscolaridadeService escolaridadeService, InscricaoMapper inscricaoMapper, SequenceNumInscricaoRepository sequenceNumInscricaoRepository, FotoAdolescenteService fotoAdolescenteService) {
        this.inscricaoRepository = inscricaoRepository;
        this.documentoService = documentoService;
        this.adolescenteService = adolescenteService;
        this.telefoneService = telefoneService;
        this.enderecoService = enderecoService;
        this.escolaridadeService = escolaridadeService;
        this.inscricaoMapper = inscricaoMapper;
        this.sequenceNumInscricaoRepository = sequenceNumInscricaoRepository;
        this.fotoAdolescenteService = fotoAdolescenteService;
    }

    @Transactional(readOnly = true)
    public StatusCpfInscricaoResponseDTO verificarCpf(String cpf) {

        String cpfLimpo = documentoService.limparDocumento(cpf);

        Optional<Documento> documentoOpt = documentoService.buscarPorDocumento(cpfLimpo);

        // 🆕 CPF nunca cadastrado
        if (documentoOpt.isEmpty()) {
            return new StatusCpfInscricaoResponseDTO(
                    StatusCpf.NOVO,
                    null
            );
        }

        Adolescente adolescente = documentoOpt.get().getAdolescente();

        // 🚫 Possui inscrição ativa
        boolean possuiAtiva =
                inscricaoRepository.existsByAdolescenteIdAdolescenteAndDataFimIsNull(
                        adolescente.getIdAdolescente()
                );

        if (possuiAtiva) {
            return new StatusCpfInscricaoResponseDTO(
                    StatusCpf.ATIVO,
                    null
            );
        }

        // ✅ Não possui inscrição ativa → pega a última inscrição finalizada
        Inscricao ultimaInscricao =
                inscricaoRepository
                        .findTopByAdolescenteIdAdolescenteOrderByDataInicioDesc(
                                adolescente.getIdAdolescente()
                        )
                        .orElse(null);

        return new StatusCpfInscricaoResponseDTO(
                StatusCpf.CRIAR,
                ultimaInscricao != null
                        ? inscricaoMapper.toResponseDTO(ultimaInscricao, LocalDate.now())
                        : inscricaoMapper.toResponseDTO(adolescente, LocalDate.now())
        );
    }

    @Transactional
    public InscricaoCriarResponseDTO criar(InscricaoCriarRequestDTO dto) {

        LocalDate dataInscricao = dto.inscricao().dataInscricao();
        if(dataInscricao == null) {
            dataInscricao = LocalDate.now();
        }

        String cpfLimpo = documentoService.limparDocumento(dto.documento().cpf());

        Adolescente adolescente;

        // 🔍 Verifica se CPF já existe
        Optional<Documento> documentoOpt = documentoService.buscarPorDocumento(cpfLimpo);

        if (documentoOpt.isPresent()) {
            // 🔁 Adolescente já existe
            adolescente = documentoOpt.get().getAdolescente();

            // 🚫 Regra de negócio principal
            boolean possuiInscricaoAtiva = inscricaoRepository.existsByAdolescenteIdAdolescenteAndDataFimIsNull(adolescente.getIdAdolescente());

            if (possuiInscricaoAtiva) {
                throw new RegraNegocioException("O adolescente já possui uma inscrição ativa.");
            }

            // Atualiza dados básicos
            adolescenteService.atualizarAdolescenteInscricao(dto.adolescente(), adolescente);

            // Atualiza dependentes somente se mudou
            enderecoService.atualizarEndereco(dto.endereco(), adolescente, dataInscricao);
            telefoneService.atualizarTelefones(dto.telefones(), adolescente, dataInscricao);
            escolaridadeService.atualizarEscolaridade(dto.escolaridade(), adolescente, dataInscricao);

        } else {
            // 🆕 Adolescente novo

            adolescente = adolescenteService.criarAdolescenteInscricao(dto.adolescente());

            if (adolescente.getIdAdolescente() == null) {
                adolescente = adolescenteService.salvar(adolescente);
            }

            documentoService.criarDocumentoInscricao(cpfLimpo, adolescente);

            enderecoService.criarEndereco(adolescente, dataInscricao,dto.endereco().cep(),dto.endereco().logradouro(),dto.endereco().numero(),dto.endereco().complemento(),dto.endereco().bairro(),dto.endereco().cidade(),dto.endereco().estado(),null);
            telefoneService.criarTelefones(dto.telefones(), adolescente, dataInscricao);

            escolaridadeService.criarEscolaridade(dto.escolaridade(), adolescente, dataInscricao);
        }

        // Cria nova inscrição
        Inscricao inscricao = criarInscricao(dto, adolescente, dataInscricao);

        Adolescente adolescenteSalvo = adolescenteService.salvar(adolescente);

        Inscricao inscricaoSalva = adolescenteSalvo.getInscricoes()
                .stream()
                .max(Comparator.comparing(Inscricao::getIdInscricao))
                .orElseThrow();

        return inscricaoMapper.toResponseDTO(inscricaoSalva, dataInscricao);
    }

    @Transactional
    public InscricaoCriarResponseDTO criar(InscricaoCriarRequestDTO dto, MultipartFile file) {

        LocalDate dataInscricao = dto.inscricao().dataInscricao();
        if(dataInscricao == null) {
            dataInscricao = LocalDate.now();
        }

        String cpfLimpo = documentoService.limparDocumento(dto.documento().cpf());

        Adolescente adolescente;

        // 🔍 Verifica se CPF já existe
        Optional<Documento> documentoOpt = documentoService.buscarPorDocumento(cpfLimpo);

        if (documentoOpt.isPresent()) {
            // 🔁 Adolescente já existe
            adolescente = documentoOpt.get().getAdolescente();

            // 🚫 Regra de negócio principal
            boolean possuiInscricaoAtiva = inscricaoRepository.existsByAdolescenteIdAdolescenteAndDataFimIsNull(adolescente.getIdAdolescente());

            if (possuiInscricaoAtiva) {
                throw new RegraNegocioException("O adolescente já possui uma inscrição ativa.");
            }

            // Atualiza dados básicos
            adolescenteService.atualizarAdolescenteInscricao(dto.adolescente(), adolescente);

            // Atualiza dependentes somente se mudou
            enderecoService.atualizarEndereco(dto.endereco(), adolescente, dataInscricao);
            telefoneService.atualizarTelefones(dto.telefones(), adolescente, dataInscricao);
            escolaridadeService.atualizarEscolaridade(dto.escolaridade(), adolescente, dataInscricao);

        } else {
            // 🆕 Adolescente novo

            adolescente = adolescenteService.criarAdolescenteInscricao(dto.adolescente());

            if (adolescente.getIdAdolescente() == null) {
                adolescente = adolescenteService.salvar(adolescente);
            }

            documentoService.criarDocumentoInscricao(cpfLimpo, adolescente);

            enderecoService.criarEndereco(adolescente, dataInscricao,dto.endereco().cep(),dto.endereco().logradouro(),dto.endereco().numero(),dto.endereco().complemento(),dto.endereco().bairro(),dto.endereco().cidade(),dto.endereco().estado(),null);
            telefoneService.criarTelefones(dto.telefones(), adolescente, dataInscricao);

            escolaridadeService.criarEscolaridade(dto.escolaridade(), adolescente, dataInscricao);
        }

        // Cria nova inscrição
        Inscricao inscricao = criarInscricao(dto, adolescente, dataInscricao);

        if (inscricao.getIdInscricao() == null) {
            inscricao = salvar(inscricao);
        }

        if (file != null && !file.isEmpty()) {
            fotoAdolescenteService.criarFotoAdolescenteInscricao(file, inscricao);
        }

        Adolescente adolescenteSalvo = adolescenteService.salvar(adolescente);

        Inscricao inscricaoSalva = adolescenteSalvo.getInscricoes()
                .stream()
                .max(Comparator.comparing(Inscricao::getIdInscricao))
                .orElseThrow();

        return inscricaoMapper.toResponseDTO(inscricaoSalva, dataInscricao);
    }

    @Transactional
    public InscricaoResponseDTO atualizar(Integer idAdolescente, Integer idInscricao, InscricaoAtualizarRequestDTO dto, MultipartFile file) {
        Inscricao inscricao = buscarInscricaoAdolescente(idAdolescente, idInscricao);

        if (file != null && !file.isEmpty()) {
            fotoAdolescenteService.atualizarFotoAdolescenteInscricao(file, inscricao);
            inscricao = salvar(inscricao);
        }

        if (Objects.equals(inscricao.getDataInicio(), dto.dataInscricao()) &&
                Objects.equals(inscricao.getObservacao(), dto.observacao())) {
            return inscricaoMapper.toResponseDTO(inscricao);
        }

        inscricao.setDataInicio(dto.dataInscricao());
        inscricao.setObservacao(dto.observacao());

        Inscricao inscricaoSalva = salvar(inscricao);

        return inscricaoMapper.toResponseDTO(inscricaoSalva);
    }

    @Transactional
    public InscricaoResponseDTO encerrarPorId(Integer idAdolescente, Integer idInscricao, InscricaoEncerrarRequestDTO dto) {
        LocalDate dataFinalizacao = dto.dataFinalizacao();
        if(dataFinalizacao == null) {
            dataFinalizacao = LocalDate.now();
        }

        Inscricao inscricao = buscarInscricaoAdolescente(idAdolescente, idInscricao);

        inscricao.setDataFim(dataFinalizacao);
        inscricao.setFinalizacao(dto.motivoFinalizacao());

        Inscricao inscricaoSalva = salvar(inscricao);

        return inscricaoMapper.toResponseDTO(inscricaoSalva);
    }

    @Transactional(readOnly = true)
    public Page<InscricaoListarResponseDTO> listarPorId(Integer idAdolescente, Pageable pageable) {
        Adolescente adolescente = adolescenteService.buscarAdolescente(idAdolescente);

        return inscricaoRepository.findByAdolescenteIdAdolescenteOrderByDataInicioDesc(idAdolescente, pageable)
                .map(inscricaoMapper::toListarResponseDTO);
    }

    @Transactional(readOnly = true)
    public InscricaoResponseDTO buscarPorId(Integer idAdolescente, Integer idInscricao) {
        Inscricao inscricao = buscarInscricaoAdolescente(idAdolescente, idInscricao);
        return inscricaoMapper.toResponseDTO(inscricao);
    }

    @Transactional(readOnly = true)
    public List<InscricaoCriarResponseDTO> listar() {
        return inscricaoRepository.findAll()
                .stream()
                .map(i -> inscricaoMapper.toResponseDTO(i, LocalDate.now()))
                .toList();
    }

    @Transactional
    public void excluirPorId(Integer idAdolescente, Integer idInscricao) {
        Inscricao inscricao = buscarInscricaoAdolescente(idAdolescente, idInscricao);
        inscricaoRepository.deleteById(idInscricao);
    }

    private Inscricao criarInscricao(InscricaoCriarRequestDTO dto, Adolescente adolescente, LocalDate data) {
        Inscricao inscricao = new Inscricao();
        inscricao.setDataInicio(data);
        inscricao.setObservacao(dto.inscricao().observacao());
        inscricao.setNumInscricao(gerarNumeroInscricao());
        adolescente.adicionarInscricao(inscricao);

        return inscricao;
    }

    public Inscricao buscarInscricaoAdolescente(Integer idAdolescente, Integer idInscricao) {
        Adolescente adolescente = adolescenteService.buscarAdolescente(idAdolescente);

        Inscricao inscricao = inscricaoRepository.findById(idInscricao)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Inscrição com ID "+idInscricao+" não encontrada."));

        if (inscricao.getAdolescente() == null) {
            throw new RegraNegocioException("Inscricao não pertence a um adolescente.");
        }

        if (!inscricao.getAdolescente().getIdAdolescente().equals(idAdolescente)) {
            throw new RegraNegocioException("Inscrição não pertence ao adolescente.");
        }

        return inscricao;
    }

    @Transactional
    public Inscricao salvar(Inscricao inscricao) {
        Inscricao salvo = inscricaoRepository.save(inscricao);
        return salvo;
    }

    @Transactional
    public Integer gerarNumeroInscricao() {
        try {
            SequenceNumInscricao seq = sequenceNumInscricaoRepository.findByNomeForUpdate("NUM_INSCRICAO")
                    .orElseThrow();

            Integer proximo = seq.getValor() + 1;
            seq.setValor(proximo);
            return proximo.intValue();

        } catch (NoSuchElementException e) {
            SequenceNumInscricao nova = new SequenceNumInscricao();
            nova.setNome("NUM_INSCRICAO");
            nova.setValor(0); //Valor Inicial
            sequenceNumInscricaoRepository.save(nova);

            return gerarNumeroInscricao(); //Tenta novamente
        }
    }

}
