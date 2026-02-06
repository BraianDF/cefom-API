package com.projeto.cefom.service;

import com.projeto.cefom.exceptions.RecursoNaoEncontradoException;
import com.projeto.cefom.exceptions.RegraNegocioException;
import com.projeto.cefom.model.Cargo;
import com.projeto.cefom.model.Curso;
import com.projeto.cefom.model.VinculoCursoCargo;
import com.projeto.cefom.repository.VinculoCursoCargoRepository;
import org.springframework.stereotype.Service;

@Service
public class VinculoCursoCargoService {
    private final CargoService cargoService;
    private final VinculoCursoCargoRepository vinculoCursoCargoRepository;

    public VinculoCursoCargoService(CargoService cargoService, VinculoCursoCargoRepository vinculoCursoCargoRepository) {
        this.cargoService = cargoService;
        this.vinculoCursoCargoRepository = vinculoCursoCargoRepository;
    }

    public void adicionarCursoCargo(Curso curso, Integer idCargo) {
        Cargo cargo = cargoService.buscarCargo(idCargo);

        if (vinculoCursoCargoRepository.existsByCursoIdCursoAndCargoIdCargo(curso.getIdCurso(), idCargo)) {
            throw new RegraNegocioException("Cargo já vinculado a esse curso.");
        }

        VinculoCursoCargo vinculo = new VinculoCursoCargo();

        if (vinculo.getIdCargoCurso() == null) {
            vinculo = vinculoCursoCargoRepository.save(vinculo);
        }

        curso.adicionarCargo(vinculo);
        cargo.adicionarCurso(vinculo);
    }

    public void removerCursoCargo(Curso curso, Integer idCargo) {
        Cargo cargo = cargoService.buscarCargo(idCargo);

        VinculoCursoCargo vinculo = vinculoCursoCargoRepository.findByCursoIdCursoAndCargoIdCargo(curso.getIdCurso(), idCargo)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Vinculo entre Curso com Id "+curso.getIdCurso()+" e Cargo com Id "+idCargo+" não encontrado."));

        curso.removerCargo(vinculo);
        cargo.removerCurso(vinculo);
    }
}
