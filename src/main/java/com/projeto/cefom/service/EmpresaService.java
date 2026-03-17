package com.projeto.cefom.service;

import com.projeto.cefom.dto.response.EmpresaListarEntrevistaResponseDTO;
import com.projeto.cefom.dto.response.EmpresaSelectResponseDTO;
import com.projeto.cefom.enums.TitularContato;
import com.projeto.cefom.exceptions.RecursoNaoEncontradoException;
import com.projeto.cefom.exceptions.RegraNegocioException;
import com.projeto.cefom.model.Documento;
import com.projeto.cefom.dto.request.EmpresaRequestDTO;
import com.projeto.cefom.dto.response.EmpresaCriarResponseDTO;
import com.projeto.cefom.dto.response.EmpresaListarResponseDTO;
import com.projeto.cefom.mapper.EmpresaMapper;
import com.projeto.cefom.model.Empresa;
import com.projeto.cefom.repository.EmpresaRepository;
import com.projeto.cefom.utils.TextoUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {
    private final EmpresaRepository empresaRepository;
    private final EmpresaMapper empresaMapper;
    private final DocumentoService documentoService;
    private final TelefoneService telefoneService;
    private final EmailService emailService;
    private final EnderecoService enderecoService;
    private final ResponsavelEmpresaService responsavelEmpresaService;
    private final TaxaAdministrativaService taxaAdministrativaService;

    public EmpresaService(EmpresaRepository empresaRepository, EmpresaMapper empresaMapper, DocumentoService documentoService, TelefoneService telefoneService, EmailService emailService, EnderecoService enderecoService, ResponsavelEmpresaService responsavelEmpresaService, TaxaAdministrativaService taxaAdministrativaService) {
        this.empresaRepository = empresaRepository;
        this.empresaMapper = empresaMapper;
        this.documentoService = documentoService;
        this.telefoneService = telefoneService;
        this.emailService = emailService;
        this.enderecoService = enderecoService;
        this.responsavelEmpresaService = responsavelEmpresaService;
        this.taxaAdministrativaService = taxaAdministrativaService;
    }

    @Transactional
    public EmpresaCriarResponseDTO criar(EmpresaRequestDTO dto) {
        LocalDate dataCriacao = dto.dataModificacao();
        if(dataCriacao == null) {
            dataCriacao = LocalDate.now();
        }

        // 🔍 Verifica se CPF ou CNPJ já existe
        Optional<Documento> documentoOpt = documentoService.buscarPorDocumento(dto.documento());

        if (documentoOpt.isPresent()) {
            if (documentoService.isCnpj(dto.documento())) {
                throw new RegraNegocioException("Empresa já cadastrada com esse CNPJ.");
            }
            if (documentoService.isCpf(dto.documento()) && documentoOpt.get().getEmpresa() != null) {
                throw new RegraNegocioException("Empresa já cadastrada com esse CPF.");
            }
        }

        Empresa empresa = criarEmpresa(dto);

        if (empresa.getIdEmpresa() == null) {
            empresa = salvar(empresa);
        }

        //Documento
        documentoService.criarDocumentoEmpresa(dto.documento(), empresa);

        //Telefone
        telefoneService.criarTelefones(dto.telefones(), empresa, dataCriacao);

        //Email
        emailService.criarEmail(dto.email(), TitularContato.EMPRESA, empresa, dataCriacao);

        //Endereço
        enderecoService.criarEndereco(empresa, dataCriacao, dto.endereco().cep(), dto.endereco().logradouro(), dto.endereco().numero(), dto.endereco().complemento(), dto.endereco().bairro(), dto.endereco().cidade(), dto.endereco().estado());

        //Responsáveis
        responsavelEmpresaService.criarResponsaveis(dto.responsaveis(), empresa, dataCriacao);

        //TaxaAdministrativa
        taxaAdministrativaService.criarTaxa(dto.taxaAdministrativa(), empresa, dataCriacao);

        Empresa empresaSalva = salvar(empresa);

        return empresaMapper.toResponseDTO(empresaSalva, dataCriacao);
    }


    public Empresa criarEmpresa(EmpresaRequestDTO dto) {
        Empresa empresa = new Empresa();

        empresa.setNumConvenio(dto.numConvenio());

        empresa.setRazaoSocial(dto.razaoSocial());
        if(dto.nomeFantasia() == null || dto.nomeFantasia().isBlank()) {
            empresa.setNomeFantasia(empresa.getRazaoSocial());
        } else {
            empresa.setNomeFantasia(dto.nomeFantasia());
        }
        if(dto.apelido() == null || dto.apelido().isBlank()) {
            empresa.setApelido(empresa.getNomeFantasia());
        } else {
            empresa.setApelido(dto.apelido());
        }

        empresa.setTipoEmpresa(dto.tipoEmpresa());
        empresa.setContratacaoPadrao(dto.contratacaoPadrao());

        return empresa;
    }

    @Transactional
    public EmpresaCriarResponseDTO atualizar(Integer idEmpresa, EmpresaRequestDTO dto) {
        LocalDate dataModificacao = dto.dataModificacao();
        if(dataModificacao == null) {
            dataModificacao = LocalDate.now();
        }

        Empresa empresa = buscarEmpresa(idEmpresa);

        atualizarEmpresa(dto, empresa);

        //Documento
        documentoService.atualizarDocumentoEmpresa(dto.documento(), empresa);

        //Telefone
        telefoneService.atualizarTelefones(dto.telefones(), empresa, dataModificacao);

        //Email
        emailService.atualizarEmail(dto.email(), empresa, dataModificacao);

        //Endereço
        enderecoService.atualizarEndereco(dto.endereco(),empresa,dataModificacao);

        //Responsáveis
        responsavelEmpresaService.atualizarResponsaveis(dto.responsaveis(), empresa, dataModificacao);

        //TaxaAdministrativa
        taxaAdministrativaService.atualizarTaxa(dto.taxaAdministrativa(), empresa, dataModificacao);

        Empresa empresaSalva = salvar(empresa);

        return empresaMapper.toResponseDTO(empresaSalva, dataModificacao);
    }

    public void atualizarEmpresa(EmpresaRequestDTO dto, Empresa empresa) {
        empresa.setNumConvenio(dto.numConvenio());

        empresa.setRazaoSocial(dto.razaoSocial());
        if(dto.nomeFantasia() == null || dto.nomeFantasia().isBlank()) {
            empresa.setNomeFantasia(empresa.getRazaoSocial());
        } else {
            empresa.setNomeFantasia(dto.nomeFantasia());
        }
        if(dto.apelido() == null || dto.apelido().isBlank()) {
            empresa.setApelido(empresa.getNomeFantasia());
        } else {
            empresa.setApelido(dto.apelido());
        }

        empresa.setTipoEmpresa(dto.tipoEmpresa());
        empresa.setContratacaoPadrao(dto.contratacaoPadrao());
    }

    @Transactional(readOnly = true)
    public List<EmpresaSelectResponseDTO> listarSelect() {
        return empresaRepository.findAll()
                .stream()
                .map(empresaMapper::toSelectResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<EmpresaListarResponseDTO> listar(Pageable pageable, String nome) {
        if (nome == null || nome.isBlank()) {
            return empresaRepository.findAll(pageable)
                    .map(empresaMapper::toListarResponseDTO);
        }
        return empresaRepository.findByApelidoContainingIgnoreCase(TextoUtils.normalizar(nome), pageable)
                .map(empresaMapper::toListarResponseDTO);
    }

    //Verificar a necessidade do método
    @Transactional(readOnly = true)
    public List<EmpresaListarEntrevistaResponseDTO> listarEntrevistas() {
        return empresaRepository.findAll()
                .stream()
                .map(empresaMapper::toListarEntrevistaResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public EmpresaCriarResponseDTO buscarPorId(Integer idEmpresa) {
        Empresa empresa = buscarEmpresa(idEmpresa);
        return empresaMapper.toResponseDTO(empresa, LocalDate.now());
    }

    @Transactional
    public void excluirPorId(Integer idEmpresa) {
        Empresa empresa = buscarEmpresa(idEmpresa);
        empresaRepository.deleteById(idEmpresa);
    }

    @Transactional(readOnly = true)
    public Empresa buscarEmpresa (Integer idEmpresa) {
        Empresa empresa = empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Empresa com ID "+idEmpresa+" não encontrada."));
        return empresa;
    }

    public Empresa salvar(Empresa empresa) {
        Empresa salvo = empresaRepository.save(empresa);
        return salvo;
    }
}
