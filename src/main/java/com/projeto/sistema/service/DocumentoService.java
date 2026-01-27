package com.projeto.sistema.service;

import com.projeto.sistema.dto.request.DocumentoAdolescenteRequestDTO;
import com.projeto.sistema.dto.response.DocumentoAdolescenteResponseDTO;
import com.projeto.sistema.dto.request.DocumentoResponsavelRequestDTO;
import com.projeto.sistema.exceptions.RecursoNaoEncontradoException;
import com.projeto.sistema.exceptions.RegraNegocioException;
import com.projeto.sistema.mapper.DocumentoMapper;
import com.projeto.sistema.model.Adolescente;
import com.projeto.sistema.model.Documento;
import com.projeto.sistema.model.Familiar;
import com.projeto.sistema.model.Empresa;
import com.projeto.sistema.repository.EmpresaRepository;
import com.projeto.sistema.repository.DocumentoRepository;
import com.projeto.sistema.repository.FamiliarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class DocumentoService {

    private final DocumentoRepository documentoRepository;
    private final DocumentoMapper documentoMapper;
    private final AdolescenteService adolescenteService;
    private final FamiliarRepository familiarRepository;
    private final EmpresaRepository empresaRepository;

    public DocumentoService(DocumentoRepository documentoRepository, DocumentoMapper documentoMapper, AdolescenteService adolescenteService, FamiliarRepository familiarRepository, EmpresaRepository empresaRepository) {
        this.documentoRepository = documentoRepository;
        this.documentoMapper = documentoMapper;
        this.adolescenteService = adolescenteService;
        this.familiarRepository = familiarRepository;
        this.empresaRepository = empresaRepository;
    }

    public DocumentoAdolescenteResponseDTO atualizar(Integer idAdolescente, DocumentoAdolescenteRequestDTO dto) {
        //Documento documento = buscarDocumentoMatricula(idAdolescente, idDocumento);

        Adolescente adolescente = adolescenteService.buscarAdolescente(idAdolescente);

        Documento documento = documentoRepository.findByAdolescenteIdAdolescente(idAdolescente)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Documento do Adolescente com ID "+idAdolescente+" não encontrado."));

        if(dto.cpf() != null && !dto.cpf().isEmpty()) {
            documento.setCpf(dto.cpf());
        }
        if(dto.ctps() != null && !dto.ctps().isEmpty()) {
            documento.setCtps(dto.ctps());
        } else {
            if(dto.cpf() != null && !dto.cpf().isEmpty()) {
                documento.setCtps(dto.cpf());
            }
        }
        if(dto.rg() != null && !dto.rg().isEmpty()) {
            documento.setRg(dto.rg());
        }
        if(dto.dataEmissaoRG() != null) {
            documento.setDataEmissaoRG(dto.dataEmissaoRG());
        }
        if(dto.nis() != null && !dto.nis().isEmpty()) {
            documento.setNis(dto.nis());
        }
        if(dto.sus() != null && !dto.sus().isEmpty()) {
            documento.setSus(dto.sus());
        }
        if(dto.tituloEleitor() != null && !dto.tituloEleitor().isEmpty()) {
            documento.setTituloEleitor(dto.tituloEleitor());
        }
        if(dto.zonaTE() != null && !dto.zonaTE().isEmpty()) {
            documento.setZonaTE(dto.zonaTE());
        }
        if(dto.secaoTE() != null && !dto.secaoTE().isEmpty()) {
            documento.setSecaoTE(dto.secaoTE());
        }
        if(dto.cnh() != null && !dto.cnh().isEmpty()) {
            documento.setCnh(dto.cnh());
        }
        if(dto.categoriaCNH() != null && !dto.categoriaCNH().isEmpty()) {
            documento.setCategoriaCNH(dto.categoriaCNH());
        }
        if(dto.reservista() != null && !dto.reservista().isEmpty()) {
            documento.setReservista(dto.reservista());
        }

        Documento documentoSalvo = salvar(documento);
        return documentoMapper.toResponseDTO(documentoSalvo);
    }

    @Transactional
    public void excluirPorId(Integer idAdolescente, Integer idDocumento) {
        Documento documento = buscarDocumentoMatricula(idAdolescente, idDocumento);
        documentoRepository.deleteById(idDocumento);
    }

    public void criarDocumentoInscricao(String cpf, Adolescente adolescente) {
        Documento documento = new Documento();
        documento.setCpf(cpf);

        adolescente.adicionarDocumento(documento);
    }

    public void atualizarDocumentoMatricula(DocumentoAdolescenteRequestDTO dto, Adolescente adolescente) {
        Documento documentoAtual = adolescente.getDocumento();

        if (documentoAtual == null) {
            criarDocumentoMatricula(dto, adolescente);
            return;
        }

        if (documentoIgualNormalizado(dto, documentoAtual)) {
            return;
        }

        atualizarCampos(dto, documentoAtual);
    }

    public void criarDocumentoMatricula(DocumentoAdolescenteRequestDTO dto, Adolescente adolescente) {
        Documento documento = new Documento();
        documento.setCpf(dto.cpf());

        if(dto.ctps() == null){
            documento.setCtps(dto.cpf());
        } else {
            documento.setCtps(dto.ctps());
        }
        if(dto.rg() != null) {
            documento.setRg(dto.rg());
        }
        if(dto.dataEmissaoRG() != null) {
            documento.setDataEmissaoRG(dto.dataEmissaoRG());
        }
        if(dto.nis() != null) {
            documento.setNis(dto.nis());
        }
        if(dto.sus() != null) {
            documento.setSus(dto.sus());
        }
        if(dto.tituloEleitor() != null) {
            documento.setTituloEleitor(dto.tituloEleitor());
        }
        if(dto.zonaTE() != null) {
            documento.setZonaTE(dto.zonaTE());
        }
        if(dto.secaoTE() != null) {
            documento.setSecaoTE(dto.secaoTE());
        }
        if(dto.cnh() != null) {
            documento.setCnh(dto.cnh());
        }
        if(dto.categoriaCNH() != null) {
            documento.setCategoriaCNH(dto.categoriaCNH());
        }
        if(dto.reservista() != null) {
            documento.setReservista(dto.reservista());
        }
        adolescente.adicionarDocumento(documento);
    }

    public void atualizarCampos(DocumentoAdolescenteRequestDTO dto, Documento d) {

        // CPF é obrigatório e sempre normalizado
        d.setCpf(dto.cpf());

        // Regra de negócio: CTPS = CPF quando não informada
        if (dto.ctps() != null) {
            d.setCtps(dto.ctps());
        } else if (d.getCtps() == null) {
            d.setCtps(d.getCpf());
        }
        if (dto.rg() != null) {
            d.setRg(dto.rg());
        }
        if (dto.dataEmissaoRG() != null) {
            d.setDataEmissaoRG(dto.dataEmissaoRG());
        }
        if (dto.nis() != null) {
            d.setNis(dto.nis());
        }
        if (dto.sus() != null) {
            d.setSus(dto.sus());
        }
        if (dto.tituloEleitor() != null) {
            d.setTituloEleitor(dto.tituloEleitor());
        }
        if (dto.zonaTE() != null) {
            d.setZonaTE(dto.zonaTE());
        }
        if (dto.secaoTE() != null) {
            d.setSecaoTE(dto.secaoTE());
        }
        if (dto.cnh() != null) {
            d.setCnh(dto.cnh());
        }
        if (dto.categoriaCNH() != null) {
            d.setCategoriaCNH(dto.categoriaCNH());
        }
        if (dto.reservista() != null) {
            d.setReservista(dto.reservista());
        }
    }

    public String limparDocumento(String documento) {
        if (documento == null || documento.isBlank()) {
            throw new RegraNegocioException("Documento não pode ser vazio.");
        }
        return documento.replaceAll("\\D", "");
    }

    public boolean isCnpj(String documento) {
        return limparDocumento(documento).length() == 14;
    }

    public boolean isCpf(String documento) {
        return limparDocumento(documento).length() == 11;
    }

    public boolean documentoIgualNormalizado(DocumentoAdolescenteRequestDTO dto, Documento d) {

        String cpfNormalizado = limparDocumento(dto.cpf());

        // Regra: se ctps não vier, usa o CPF
        String ctpsNormalizada =
                dto.ctps() != null ? dto.ctps() : cpfNormalizado;

        return Objects.equals(d.getCpf(), cpfNormalizado)
                && Objects.equals(d.getCtps(), ctpsNormalizada)
                && Objects.equals(d.getRg(), dto.rg())
                && Objects.equals(d.getDataEmissaoRG(), dto.dataEmissaoRG())
                && Objects.equals(d.getNis(), dto.nis())
                && Objects.equals(d.getSus(), dto.sus())
                && Objects.equals(d.getTituloEleitor(), dto.tituloEleitor())
                && Objects.equals(d.getZonaTE(), dto.zonaTE())
                && Objects.equals(d.getSecaoTE(), dto.secaoTE())
                && Objects.equals(d.getCnh(), dto.cnh())
                && Objects.equals(d.getCategoriaCNH(), dto.categoriaCNH())
                && Objects.equals(d.getReservista(), dto.reservista());
    }

    @Transactional(readOnly = true)
    public Optional<Documento> buscarPorDocumento(String documento) {
        String documentoLimpo = limparDocumento(documento);
        if (isCnpj(documento)) {
            return documentoRepository.findByCnpj(documentoLimpo);
        } else if (isCpf(documento)) {
            return documentoRepository.findByCpf(documentoLimpo);
        } else {
            throw new RegraNegocioException("Documento inválido. Deve conter 11 (CPF) ou 14 (CNPJ) dígitos.");
        }
    }

    public Documento criarOuAtualizarDocumentoResponsavel(DocumentoResponsavelRequestDTO dto, Familiar responsavel) {

        Documento documentoAtual = responsavel.getDocumento();

        if (documentoAtual == null) {
            Documento novo = new Documento();
            preencherDocumentoResponsavel(dto, novo);

            responsavel.adicionarDocumento(novo);
            return novo;
        }

        if (documentoResponsavelIgual(dto, documentoAtual)) {
            return documentoAtual;
        }

        preencherDocumentoResponsavel(dto, documentoAtual);
        return documentoAtual;
    }

    private void preencherDocumentoResponsavel(DocumentoResponsavelRequestDTO dto, Documento d) {
        d.setCpf(dto.cpf());

        if (dto.rg() != null) {
            d.setRg(dto.rg());
        }

        if (dto.nis() != null) {
            d.setNis(dto.nis());
        }
    }

    private boolean documentoResponsavelIgual(DocumentoResponsavelRequestDTO dto, Documento d) {
        return Objects.equals(d.getCpf(), limparDocumento(dto.cpf())) &&
                Objects.equals(d.getRg(), dto.rg()) &&
                Objects.equals(d.getNis(), dto.nis());
    }

    public Documento buscarDocumentoMatricula(Integer idAdolescente, Integer idDocumento) {
        Adolescente adolescente = adolescenteService.buscarAdolescente(idAdolescente);

        Documento documento = documentoRepository.findById(idDocumento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Documento com ID "+idDocumento+" não encontrado."));

        if (documento.getAdolescente() == null) {
            throw new RegraNegocioException("Documento não pertence a um adolescente.");
        }

        if (!documento.getAdolescente().getIdAdolescente().equals(idAdolescente)) {
            throw new RegraNegocioException("Documento não pertence ao adolescente.");
        }

        return documento;
    }

    @Transactional
    public Documento salvar(Documento documento) {
        Documento salvo = documentoRepository.save(documento);
        return salvo;
    }

    public void criarDocumentoEmpresa(String documento, Empresa empresa) {
        Documento doc = new Documento();

        if (isCnpj(documento)) {
            doc.setCnpj(documento);
        } else if (isCpf(documento)) {
            doc.setCpf(documento);
        } else {
            throw new RegraNegocioException("Documento inválido. Deve conter 11 (CPF) ou 14 (CNPJ) dígitos.");
        }
        empresa.adicionarDocumento(doc);
    }

    public void atualizarDocumentoEmpresa(String documento, Empresa empresa) {
        Documento documentoAtual = empresa.getDocumento();
        String docLimpo = limparDocumento(documento);

        if (documentoAtual == null) {
            criarDocumentoEmpresa(docLimpo, empresa);
            return;
        }

        if (isCpf(docLimpo)) {
            if (!Objects.equals(documentoAtual.getCpf(), docLimpo)) {
                documentoAtual.setCpf(docLimpo);
                documentoAtual.setCnpj(null);
            }
        } else if (isCnpj(docLimpo)) {
            if (!Objects.equals(documentoAtual.getCnpj(), docLimpo)) {
                documentoAtual.setCnpj(docLimpo);
                documentoAtual.setCpf(null);
            }
        } else {
            throw new RegraNegocioException("Documento inválido.");
        }
    }

    private Familiar buscarFamiliarAdolescente(Integer idAdolescente, Integer idFamiliar) {
        Adolescente adolescente = adolescenteService.buscarAdolescente(idAdolescente);

        Familiar familiar = familiarRepository.findById(idFamiliar)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Familiar com ID "+idFamiliar+" não encontrada."));

        if (familiar.getAdolescente() == null) {
            throw new RegraNegocioException("Familiar não pertence a um adolescente.");
        }

        if (!familiar.getAdolescente().getIdAdolescente().equals(idAdolescente)) {
            throw new RegraNegocioException("Familiar não pertence ao adolescente.");
        }

        return familiar;
    }

    public Documento buscarDocumentoFamiliar(Integer idAdolescente, Integer idFamiliar, Integer idDocumento) {
        Familiar familiar = buscarFamiliarAdolescente(idAdolescente, idFamiliar);

        Documento documento = documentoRepository.findById(idDocumento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Documento com ID "+idDocumento+" não encontrado."));

        if (documento.getFamiliar() == null) {
            throw new RegraNegocioException("Documento não pertence a um familiar.");
        }

        if (!documento.getFamiliar().getIdFamiliar().equals(idFamiliar)) {
            throw new RegraNegocioException("Documento não pertence ao familiar.");
        }

        return documento;
    }

    @Transactional
    public void excluirFamiliarPorId(Integer idAdolescente, Integer idFamiliar, Integer idDocumento) {
        Documento documento = buscarDocumentoFamiliar(idAdolescente, idFamiliar, idDocumento);
        documentoRepository.deleteById(idDocumento);
    }

    private Empresa buscarEmpresa(Integer idEmpresa) {
        Empresa empresa = empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Empresa com ID "+idEmpresa+" não encontrada."));
        return empresa;
    }

    public Documento buscarDocumentoEmpresa(Integer idEmpresa, Integer idDocumento) {
        Empresa empresa = buscarEmpresa(idEmpresa);

        Documento documento = documentoRepository.findById(idDocumento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Documento com ID "+idDocumento+" não encontrado."));

        if (documento.getEmpresa() == null) {
            throw new RegraNegocioException("Documento não pertence a uma empresa.");
        }

        if (!documento.getEmpresa().getIdEmpresa().equals(idEmpresa)) {
            throw new RegraNegocioException("Documento não pertence a empresa.");
        }

        return documento;
    }


    @Transactional
    public void excluirEmpresaPorId(Integer idEmpresa, Integer idDocumento) {
        Documento documento = buscarDocumentoEmpresa(idEmpresa, idDocumento);
        documentoRepository.deleteById(idDocumento);
    }

}
