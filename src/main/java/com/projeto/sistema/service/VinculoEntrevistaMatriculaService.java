package com.projeto.sistema.service;

import com.projeto.sistema.exceptions.RecursoNaoEncontradoException;
import com.projeto.sistema.exceptions.RegraNegocioException;
import com.projeto.sistema.model.Entrevista;
import com.projeto.sistema.model.Matricula;
import com.projeto.sistema.dto.request.VinculoEntrevistaMatriculaRequestDTO;
import com.projeto.sistema.model.VinculoEntrevistaMatricula;
import com.projeto.sistema.repository.VinculoEntrevistaMatriculaRepository;
import com.projeto.sistema.service.MatriculaService;
import org.springframework.stereotype.Service;

@Service
public class VinculoEntrevistaMatriculaService {
    private final VinculoEntrevistaMatriculaRepository vinculoEntrevistaMatriculaRepository;
    private final MatriculaService matriculaService;

    public VinculoEntrevistaMatriculaService(VinculoEntrevistaMatriculaRepository vinculoEntrevistaMatriculaRepository, MatriculaService matriculaService) {
        this.vinculoEntrevistaMatriculaRepository = vinculoEntrevistaMatriculaRepository;
        this.matriculaService = matriculaService;
    }

    public void adicionarEntrevistaMatricula(Entrevista entrevista, VinculoEntrevistaMatriculaRequestDTO dto) {
        Matricula matricula = matriculaService.buscarMatricula(dto.idMatricula());

        if (vinculoEntrevistaMatriculaRepository.existsByEntrevistaIdEntrevistaAndMatriculaIdMatricula(entrevista.getIdEntrevista(), dto.idMatricula())) {
            throw new RegraNegocioException("Entrevista já vinculado a esse Adolescente.");
        }

        VinculoEntrevistaMatricula vinculo = new VinculoEntrevistaMatricula();
        vinculo.setHorarioEntrevista(dto.horarioEntrevista());
        vinculo.setSituacao(dto.situacao());

        if (vinculo.getIdVinculoEntrevistaMatricula() == null) {
            vinculo = vinculoEntrevistaMatriculaRepository.save(vinculo);
        }

        entrevista.adicionarAdolescente(vinculo);
        matricula.adicionarEntrevista(vinculo);
    }

    public void atualizarEntrevistaMatricula(Entrevista entrevista, VinculoEntrevistaMatriculaRequestDTO dto) {
        Matricula matricula = matriculaService.buscarMatricula(dto.idMatricula());

        VinculoEntrevistaMatricula vinculo = vinculoEntrevistaMatriculaRepository.findByEntrevistaIdEntrevistaAndMatriculaIdMatricula(entrevista.getIdEntrevista(), dto.idMatricula())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Vinculo entre Entrevista com Id "+entrevista.getIdEntrevista()+" e Matricula com Id "+dto.idMatricula()+" não encontrado."));

        vinculo.setHorarioEntrevista(dto.horarioEntrevista());
        vinculo.setSituacao(dto.situacao());
    }

    public void removerEntrevistaMatricula(Entrevista entrevista, Integer idMatricula) {
        Matricula matricula = matriculaService.buscarMatricula(idMatricula);

        VinculoEntrevistaMatricula vinculo = vinculoEntrevistaMatriculaRepository.findByEntrevistaIdEntrevistaAndMatriculaIdMatricula(entrevista.getIdEntrevista(), idMatricula)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Vinculo entre Entrevista com Id "+entrevista.getIdEntrevista()+" e Matricula com Id "+idMatricula+" não encontrado."));

        entrevista.removerAdolescente(vinculo);
        matricula.removerEntrevista(vinculo);
    }
}
