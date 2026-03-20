package com.projeto.cefom.service;

import com.projeto.cefom.dto.request.MatriculaAtualizarRequestDTO;
import com.projeto.cefom.dto.request.MatriculaCriarRequestDTO;
import com.projeto.cefom.dto.request.MatriculaEncerrarRequestDTO;
import com.projeto.cefom.dto.response.*;
import com.projeto.cefom.enums.StatusCpf;
import com.projeto.cefom.enums.TitularContato;
import com.projeto.cefom.exceptions.RecursoNaoEncontradoException;
import com.projeto.cefom.exceptions.RegraNegocioException;
import com.projeto.cefom.image.service.FotoAdolescenteService;
import com.projeto.cefom.mapper.MatriculaMapper;
import com.projeto.cefom.model.Adolescente;
import com.projeto.cefom.model.Documento;
import com.projeto.cefom.model.Matricula;
import com.projeto.cefom.model.SequenceNumMatricula;
import com.projeto.cefom.repository.MatriculaRepository;
import com.projeto.cefom.repository.SequenceNumMatriculaRepository;
import com.projeto.cefom.utils.TextoUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;

@Service
public class MatriculaService {
    private final MatriculaRepository matriculaRepository;
    private final MatriculaMapper matriculaMapper;
    private final SequenceNumMatriculaRepository sequenceNumMatriculaRepository;
    private final DocumentoService documentoService;
    private final AdolescenteService adolescenteService;
    private final TelefoneService telefoneService;
    private final EnderecoService enderecoService;
    private final EscolaridadeService escolaridadeService;
    private final EmailService emailService;
    private final CaracteristicaService caracteristicaService;
    private final DadosSocialService dadosSocialService;
    private final FamiliarService familiarService;
    private final FotoAdolescenteService fotoAdolescenteService;

    public MatriculaService(MatriculaRepository matriculaRepository, MatriculaMapper matriculaMapper, SequenceNumMatriculaRepository sequenceNumMatriculaRepository, DocumentoService documentoService, AdolescenteService adolescenteService, TelefoneService telefoneService, EnderecoService enderecoService, EscolaridadeService escolaridadeService, EmailService emailService, CaracteristicaService caracteristicaService, DadosSocialService dadosSocialService, FamiliarService familiarService, FotoAdolescenteService fotoAdolescenteService) {
        this.matriculaRepository = matriculaRepository;
        this.matriculaMapper = matriculaMapper;
        this.sequenceNumMatriculaRepository = sequenceNumMatriculaRepository;
        this.documentoService = documentoService;
        this.adolescenteService = adolescenteService;
        this.telefoneService = telefoneService;
        this.enderecoService = enderecoService;
        this.escolaridadeService = escolaridadeService;
        this.emailService = emailService;
        this.caracteristicaService = caracteristicaService;
        this.dadosSocialService = dadosSocialService;
        this.familiarService = familiarService;
        this.fotoAdolescenteService = fotoAdolescenteService;
    }

    @Transactional(readOnly = true)
    public StatusCpfMatriculaResponseDTO verificarCpf(String cpf) {
        String cpfLimpo = TextoUtils.manterSomenteNumeros(cpf);

        Optional<Documento> documentoOpt = documentoService.buscarPorDocumento(cpfLimpo);

        if (documentoOpt.isEmpty()) {
            return new StatusCpfMatriculaResponseDTO(
                    StatusCpf.NOVO,
                    null
            );
        }

        Adolescente adolescente = documentoOpt.get().getAdolescente();

        boolean possuiAtiva =
                matriculaRepository.existsByAdolescenteIdAdolescenteAndDataFimIsNull(
                        adolescente.getIdAdolescente()
                );

        if (possuiAtiva) {
            return new StatusCpfMatriculaResponseDTO(
                    StatusCpf.ATIVO,
                    null
            );
        }

        Matricula ultimaMatricula =
                matriculaRepository
                        .findTopByAdolescenteIdAdolescenteOrderByDataInicioDesc(
                                adolescente.getIdAdolescente()
                        )
                        .orElse(null);

        return new StatusCpfMatriculaResponseDTO(
                StatusCpf.CRIAR,
                ultimaMatricula != null
                        ? matriculaMapper.toResponseDTO(ultimaMatricula, LocalDate.now())
                        : matriculaMapper.toResponseDTO(adolescente, LocalDate.now())
        );
    }

    @Transactional
    public MatriculaCriarResponseDTO criar(MatriculaCriarRequestDTO dto) {

        LocalDate dataMatricula = dto.matricula().dataMatricula();
        if(dataMatricula == null) {
            dataMatricula = LocalDate.now();
        }

        String cpfLimpo = TextoUtils.manterSomenteNumeros(dto.documento().cpf());

        Adolescente adolescente;

        Optional<Documento> documentoOpt = documentoService.buscarPorDocumento(cpfLimpo);

        if (documentoOpt.isPresent()) {
            //Adolescente já existe

            adolescente = documentoOpt.get().getAdolescente();

            //Verifica se existe matricula ativa
            boolean possuiMatriculaAtiva = matriculaRepository.existsByAdolescenteIdAdolescenteAndDataFimIsNull(adolescente.getIdAdolescente());

            if (possuiMatriculaAtiva) {
                throw new RegraNegocioException("O adolescente já possui uma matrícula ativa.");
            }

            //Atualiza dados básicos
            adolescenteService.atualizarAdolescenteMatricula(dto.adolescente(), adolescente);

            //Atualiza dependentes somente se mudou
            documentoService.atualizarDocumentoMatricula(dto.documento(), adolescente);
            enderecoService.atualizarEnderecoMatricula(dto.endereco(), adolescente, dataMatricula);
            telefoneService.atualizarTelefones(dto.telefones(), adolescente, dataMatricula);
            escolaridadeService.atualizarEscolaridade(dto.escolaridade(), adolescente, dataMatricula);
            emailService.atualizarEmail(dto.emailAdolescente(),adolescente, dataMatricula);
            caracteristicaService.atualizarCaracteristica(dto.caracteristica(),adolescente, dataMatricula);
            dadosSocialService.atualizarDadosSocial(dto.dadosSocial(), adolescente, dataMatricula);
            familiarService.atualizarFamiliares(dto.familiares(), adolescente, dataMatricula);
        } else {
            //Adolescente novo

            adolescente = adolescenteService.criarAdolescenteMatricula(dto.adolescente());

            if (adolescente.getIdAdolescente() == null) {
                adolescente = adolescenteService.salvar(adolescente);
            }

            documentoService.criarDocumentoMatricula(dto.documento(), adolescente);
            enderecoService.criarEndereco(adolescente,dataMatricula,dto.endereco().cep(),dto.endereco().logradouro(),dto.endereco().numero(),dto.endereco().complemento(),dto.endereco().bairro(),dto.endereco().cidade(),dto.endereco().estado(),null);
            telefoneService.criarTelefones(dto.telefones(), adolescente, dataMatricula);
            escolaridadeService.criarEscolaridade(dto.escolaridade(), adolescente, dataMatricula);
            emailService.criarEmail(dto.emailAdolescente(), TitularContato.ADOLESCENTE, adolescente, dataMatricula);
            caracteristicaService.criarCaracteristica(dto.caracteristica(), adolescente, dataMatricula);
            dadosSocialService.criarDadosSocial(dto.dadosSocial(), adolescente, dataMatricula);
            familiarService.criarFamiliares(dto.familiares(), adolescente, dataMatricula);
        }

        //Cria nova matrícula
        Matricula matricula = criarMatricula(dto, adolescente, dataMatricula);

        Adolescente adolescenteSalvo = adolescenteService.salvar(adolescente);

        Matricula matriculaSalva = adolescenteSalvo.getMatriculas()
                .stream()
                .max(Comparator.comparing(Matricula::getIdMatricula))
                .orElseThrow();

        return matriculaMapper.toResponseDTO(matriculaSalva, dataMatricula);

    }

    @Transactional
    public MatriculaCriarResponseDTO criar(MatriculaCriarRequestDTO dto, MultipartFile file) {

        LocalDate dataMatricula = dto.matricula().dataMatricula();
        if(dataMatricula == null) {
            dataMatricula = LocalDate.now();
        }

        String cpfLimpo = TextoUtils.manterSomenteNumeros(dto.documento().cpf());

        Adolescente adolescente;

        Optional<Documento> documentoOpt = documentoService.buscarPorDocumento(cpfLimpo);

        if (documentoOpt.isPresent()) {
            //Adolescente já existe

            adolescente = documentoOpt.get().getAdolescente();

            //Verifica se existe matricula ativa
            boolean possuiMatriculaAtiva = matriculaRepository.existsByAdolescenteIdAdolescenteAndDataFimIsNull(adolescente.getIdAdolescente());

            if (possuiMatriculaAtiva) {
                throw new RegraNegocioException("O adolescente já possui uma matrícula ativa.");
            }

            //Atualiza dados básicos
            adolescenteService.atualizarAdolescenteMatricula(dto.adolescente(), adolescente);

            //Atualiza dependentes somente se mudou
            documentoService.atualizarDocumentoMatricula(dto.documento(), adolescente);
            enderecoService.atualizarEnderecoMatricula(dto.endereco(), adolescente, dataMatricula);
            telefoneService.atualizarTelefones(dto.telefones(), adolescente, dataMatricula);
            escolaridadeService.atualizarEscolaridade(dto.escolaridade(), adolescente, dataMatricula);
            emailService.atualizarEmail(dto.emailAdolescente(),adolescente, dataMatricula);
            caracteristicaService.atualizarCaracteristica(dto.caracteristica(),adolescente, dataMatricula);
            dadosSocialService.atualizarDadosSocial(dto.dadosSocial(), adolescente, dataMatricula);
            familiarService.atualizarFamiliares(dto.familiares(), adolescente, dataMatricula);
        } else {
            //Adolescente novo

            adolescente = adolescenteService.criarAdolescenteMatricula(dto.adolescente());

            if (adolescente.getIdAdolescente() == null) {
                adolescente = adolescenteService.salvar(adolescente);
            }

            documentoService.criarDocumentoMatricula(dto.documento(), adolescente);
            enderecoService.criarEndereco(adolescente,dataMatricula,dto.endereco().cep(),dto.endereco().logradouro(),dto.endereco().numero(),dto.endereco().complemento(),dto.endereco().bairro(),dto.endereco().cidade(),dto.endereco().estado(),null);
            telefoneService.criarTelefones(dto.telefones(), adolescente, dataMatricula);
            escolaridadeService.criarEscolaridade(dto.escolaridade(), adolescente, dataMatricula);
            emailService.criarEmail(dto.emailAdolescente(), TitularContato.ADOLESCENTE, adolescente, dataMatricula);
            caracteristicaService.criarCaracteristica(dto.caracteristica(), adolescente, dataMatricula);
            dadosSocialService.criarDadosSocial(dto.dadosSocial(), adolescente, dataMatricula);
            familiarService.criarFamiliares(dto.familiares(), adolescente, dataMatricula);
        }

        //Cria nova matrícula
        Matricula matricula = criarMatricula(dto, adolescente, dataMatricula);

        if (matricula.getIdMatricula() == null) {
            matricula = salvar(matricula);
        }

        if (file != null && !file.isEmpty()) {
            fotoAdolescenteService.criarFotoAdolescenteMatricula(file, matricula);
        }

        Adolescente adolescenteSalvo = adolescenteService.salvar(adolescente);

        Matricula matriculaSalva = adolescenteSalvo.getMatriculas()
                .stream()
                .max(Comparator.comparing(Matricula::getIdMatricula))
                .orElseThrow();

        return matriculaMapper.toResponseDTO(matriculaSalva, dataMatricula);

    }

    @Transactional
    public MatriculaResponseDTO atualizar(Integer idAdolescente, Integer idMatricula, MatriculaAtualizarRequestDTO dto, MultipartFile file) {
        Matricula matricula = buscarMatriculaAdolescente(idAdolescente, idMatricula);

        if (file != null && !file.isEmpty()) {
            fotoAdolescenteService.atualizarFotoAdolescenteMatricula(file, matricula);
            matricula = salvar(matricula);
        }

        if (Objects.equals(matricula.getDataInicio(), dto.dataMatricula()) &&
                Objects.equals(matricula.getSituacaoMatricula(), dto.situacaoMatricula())) {
            return matriculaMapper.toResponseDTO(matricula);
        }

        matricula.setSituacaoMatricula(dto.situacaoMatricula());
        matricula.setDataInicio(dto.dataMatricula());

        Matricula matriculaSalva = salvar(matricula);

        return matriculaMapper.toResponseDTO(matriculaSalva);
    }

    @Transactional
    public MatriculaResponseDTO encerrarPorId(Integer idAdolescente, Integer idMatricula, MatriculaEncerrarRequestDTO dto) {
        LocalDate dataDesligamento = dto.dataDesligamento();
        if(dataDesligamento == null) {
            dataDesligamento = LocalDate.now();
        }

        Matricula matricula = buscarMatriculaAdolescente(idAdolescente, idMatricula);

        // Marca a matrícula como desligada
        matricula.setDataFim(dataDesligamento);
        matricula.setDesligamento(dto.MotivoDesligamento());

        Matricula matriculaSalva = salvar(matricula);

        return matriculaMapper.toResponseDTO(matriculaSalva);
    }

    @Transactional(readOnly = true)
    public MatriculaResponseDTO buscarPorId(Integer idAdolescente, Integer idMatricula) {
        Matricula matricula = buscarMatriculaAdolescente(idAdolescente, idMatricula);
        return matriculaMapper.toResponseDTO(matricula);
    }

    @Transactional(readOnly = true)
    public Page<MatriculaListarResponseDTO> listarPorId(Integer idAdolescente, Pageable pageable) {
        Adolescente adolescente = adolescenteService.buscarAdolescente(idAdolescente);

        return matriculaRepository.findByAdolescenteIdAdolescenteOrderByDataInicioDesc(idAdolescente, pageable)
                .map(matriculaMapper::toListarResponseDTO);
    }

    @Transactional(readOnly = true)
    public List<MatriculaCriarResponseDTO> listar() {
        return matriculaRepository.findAll()
                .stream()
                .map(m -> matriculaMapper.toResponseDTO(m, LocalDate.now()))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MatriculaSelectResponseDTO> listarSelect() {
        return matriculaRepository.findAll()
                .stream()
                .filter(m -> m.estaValidoEm(LocalDate.now()))
                .map(matriculaMapper::toSelectResponseDTO)
                .toList();
    }

    @Transactional
    public void excluirPorId(Integer idAdolescente, Integer idMatricula) {
        Matricula matricula = buscarMatriculaAdolescente(idAdolescente, idMatricula);
        matriculaRepository.deleteById(idMatricula);
    }

    public Matricula criarMatricula(MatriculaCriarRequestDTO dto, Adolescente adolescente, LocalDate data) {
        Matricula matricula = new Matricula();
        matricula.setDataInicio(data);
        matricula.setSituacaoMatricula(dto.matricula().situacaoMatricula());
        matricula.setNumMatricula(gerarNumeroMatricula());
        adolescente.adicionarMatricula(matricula);

        return matricula;
    }

    public Matricula buscarMatriculaAdolescente(Integer idAdolescente, Integer idMatricula) {
        Adolescente adolescente = adolescenteService.buscarAdolescente(idAdolescente);

        Matricula matricula = buscarMatricula(idMatricula);

        if (matricula.getAdolescente() == null) {
            throw new RegraNegocioException("Matrícula não pertence a um adolescente.");
        }

        if (!matricula.getAdolescente().getIdAdolescente().equals(idAdolescente)) {
            throw new RegraNegocioException("Matrícula não pertence ao adolescente.");
        }

        return matricula;
    }

    public Matricula buscarMatricula(Integer idMatricula) {
        Matricula matricula = matriculaRepository.findById(idMatricula)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Matrícula com ID "+idMatricula+" não encontrada."));
        return matricula;
    }

    @Transactional
    public Matricula salvar(Matricula matricula) {
        Matricula salvo = matriculaRepository.save(matricula);
        return salvo;
    }

    @Transactional
    public Integer gerarNumeroMatricula() {

        try {
            SequenceNumMatricula seq = sequenceNumMatriculaRepository.findByNomeForUpdate("NUM_MATRICULA")
                    .orElseThrow();

            Integer proximo = seq.getValor() + 1;
            seq.setValor(proximo);
            return proximo.intValue();

        } catch (NoSuchElementException e) {
            SequenceNumMatricula nova = new SequenceNumMatricula();
            nova.setNome("NUM_MATRICULA");
            nova.setValor(1000); //Valor Inicial
            sequenceNumMatriculaRepository.save(nova);

            return gerarNumeroMatricula(); //Tenta novamente
        }

    }
}
