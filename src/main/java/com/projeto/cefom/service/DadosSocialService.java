package com.projeto.cefom.service;

import com.projeto.cefom.dto.request.DadosSocialAtualizarRequestDTO;
import com.projeto.cefom.dto.request.DadosSocialRequestDTO;
import com.projeto.cefom.dto.response.DadosSocialListarResponseDTO;
import com.projeto.cefom.dto.response.DadosSocialResponseDTO;
import com.projeto.cefom.exceptions.RecursoNaoEncontradoException;
import com.projeto.cefom.exceptions.RegraNegocioException;
import com.projeto.cefom.mapper.DadosSocialMapper;
import com.projeto.cefom.model.Adolescente;
import com.projeto.cefom.model.DadosSocial;
import com.projeto.cefom.repository.DadosSocialRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

@Service
public class DadosSocialService {

    private final DadosSocialRepository dadosSocialRepository;
    private final AdolescenteService adolescenteService;
    private final DadosSocialMapper dadosSocialMapper;

    public DadosSocialService(DadosSocialRepository dadosSocialRepository, AdolescenteService adolescenteService, DadosSocialMapper dadosSocialMapper) {
        this.dadosSocialRepository = dadosSocialRepository;
        this.adolescenteService = adolescenteService;
        this.dadosSocialMapper = dadosSocialMapper;
    }

    @Transactional
    public DadosSocialResponseDTO atualizar(Integer idAdolescente, DadosSocialAtualizarRequestDTO dto) {
        LocalDate dataModificacao = dto.dataModificacao();
        if(dataModificacao == null) {
            dataModificacao = LocalDate.now();
        }

        //Verifica se o adolescente existe e retorna ele
        Adolescente adolescente = adolescenteService.buscarAdolescente(idAdolescente);

        //Atualiza
        atualizarDadosSocial(dto.dadosSocial(), adolescente, dataModificacao);

        //Salva o adolescente no banco
        Adolescente adolescenteSalvo = adolescenteService.salvar(adolescente);

        //Retorna o ResponseDTO
        return dadosSocialMapper.toResponseDTO(adolescenteSalvo, dataModificacao);
    }

    @Transactional(readOnly = true)
    public Page<DadosSocialListarResponseDTO> listar(Integer idAdolescente, Pageable pageable) {
        //Verifica se o adolescente existe e retorna ele
        Adolescente adolescente = adolescenteService.buscarAdolescente(idAdolescente);

        //Retorna todos que o adolescente tiver
        return dadosSocialRepository.findByAdolescenteIdAdolescenteOrderByDataInicioDesc(idAdolescente, pageable)
                .map(dadosSocialMapper::toListarResponseDTO);
    }

    @Transactional(readOnly = true)
    public DadosSocialResponseDTO buscarPorId(Integer idAdolescente, Integer idDadosSocial) {
        DadosSocial dadosSocial = buscarDadosSocialAdolescente(idAdolescente, idDadosSocial);
        return dadosSocialMapper.toResponseDTO(dadosSocial);
    }

    @Transactional
    public void excluirPorId(Integer idAdolescente, Integer idDadosSocial) {
        DadosSocial dadosSocial = buscarDadosSocialAdolescente(idAdolescente, idDadosSocial);
        dadosSocialRepository.deleteById(idDadosSocial);
    }

    public void atualizarDadosSocial(DadosSocialRequestDTO dto, Adolescente adolescente, LocalDate data) {

        DadosSocial dadosSocialAtual = adolescente.getDadosSociais().stream()
                .filter(d -> d.getDataFim() == null)
                .max(Comparator.comparing(DadosSocial::getDataInicio))
                .orElse(null);

        if (dadosSocialAtual != null && dadosSocialIgual(dto, dadosSocialAtual)) {
            return;
        }

        if (dadosSocialAtual != null) {
            dadosSocialAtual.setDataFim(data);
        }

        criarDadosSocial(dto, adolescente, data);
    }

    public void criarDadosSocial(DadosSocialRequestDTO dto, Adolescente adolescente, LocalDate data) {
        DadosSocial d = new DadosSocial();
        if (dto.comportamentoBoolean() != null) {
            d.setComportamentoBoolean(dto.comportamentoBoolean());
        }
        if (dto.comportamento() != null) {
            d.setComportamento(dto.comportamento());
        }
        if (dto.encaminhamentoBoolean() != null) {
            d.setEncaminhamentoBoolean(dto.encaminhamentoBoolean());
        }
        if (dto.encaminhamento() != null) {
            d.setEncaminhamento(dto.encaminhamento());
        }
        if (dto.beneficioBoolean() != null) {
            d.setBeneficioBoolean(dto.beneficioBoolean());
        }
        if (dto.beneficio() != null) {
            d.setBeneficio(dto.beneficio());
        }
        if (dto.beneficioValor() != null) {
            d.setBeneficioValor(dto.beneficioValor());
        }
        if (dto.problemaSaudeBoolean() != null) {
            d.setProblemaSaudeBoolean(dto.problemaSaudeBoolean());
        }
        if (dto.problemaSaude() != null) {
            d.setProblemaSaude(dto.problemaSaude());
        }
        if (dto.medicamentoBoolean() != null) {
            d.setMedicamentoBoolean(dto.medicamentoBoolean());
        }
        if (dto.medicamento() != null) {
            d.setMedicamento(dto.medicamento());
        }
        if (dto.entidadeBoolean() != null) {
            d.setEntidadeBoolean(dto.entidadeBoolean());
        }
        if (dto.entidade() != null) {
            d.setEntidade(dto.entidade());
        }
        d.setDataInicio(data);

        adolescente.adicionarDadosSocial(d);
    }

    public boolean dadosSocialIgual(DadosSocialRequestDTO dto, DadosSocial d) {

        return Objects.equals(d.getComportamentoBoolean(), dto.comportamentoBoolean())
                && Objects.equals(d.getComportamento(), dto.comportamento())
                && Objects.equals(d.getEncaminhamentoBoolean(), dto.encaminhamentoBoolean())
                && Objects.equals(d.getEncaminhamento(), dto.encaminhamento())
                && Objects.equals(d.getBeneficioBoolean(), dto.beneficioBoolean())
                && Objects.equals(d.getBeneficio(), dto.beneficio())
                && Objects.equals(d.getBeneficioValor(), dto.beneficioValor())
                && Objects.equals(d.getProblemaSaudeBoolean(), dto.problemaSaudeBoolean())
                && Objects.equals(d.getProblemaSaude(), dto.problemaSaude())
                && Objects.equals(d.getMedicamentoBoolean(), dto.medicamentoBoolean())
                && Objects.equals(d.getMedicamento(), dto.medicamento())
                && Objects.equals(d.getEntidadeBoolean(), dto.entidadeBoolean())
                && Objects.equals(d.getEntidade(), dto.entidade());
    }

    private DadosSocial buscarDadosSocialAdolescente(Integer idAdolescente, Integer idDadosSocial) {
        Adolescente adolescente = adolescenteService.buscarAdolescente(idAdolescente);

        DadosSocial dadosSocial = dadosSocialRepository.findById(idDadosSocial)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Dados Social com ID "+idDadosSocial+" não encontrado."));

        if (dadosSocial.getAdolescente() == null) {
            throw new RegraNegocioException("Dados Social não pertence a um adolescente.");
        }

        if (!dadosSocial.getAdolescente().getIdAdolescente().equals(idAdolescente)) {
            throw new RegraNegocioException("Dados Social não pertence ao adolescente.");
        }

        return dadosSocial;
    }
}
