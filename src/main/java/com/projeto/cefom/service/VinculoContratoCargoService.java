package com.projeto.cefom.service;

import com.projeto.cefom.exceptions.RecursoNaoEncontradoException;
import com.projeto.cefom.model.Cargo;
import com.projeto.cefom.model.Contrato;
import com.projeto.cefom.model.VinculoContratoCargo;
import com.projeto.cefom.repository.VinculoContratoCargoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class VinculoContratoCargoService {
    private final CargoService cargoService;
    private final VinculoContratoCargoRepository vinculoContratoCargoRepository;

    public VinculoContratoCargoService(CargoService cargoService, VinculoContratoCargoRepository vinculoContratoCargoRepository) {
        this.cargoService = cargoService;
        this.vinculoContratoCargoRepository = vinculoContratoCargoRepository;
    }

    public void adicionarContratoCargo(Contrato contrato, Integer idCargo, LocalDate data) {
        Cargo cargo = cargoService.buscarCargo(idCargo);

        VinculoContratoCargo vinculo = new VinculoContratoCargo();
        vinculo.setDataInicio(data);

        if (vinculo.getIdCargoContrato() == null) {
            vinculo = vinculoContratoCargoRepository.save(vinculo);
        }

        contrato.adicionarCargo(vinculo);
        cargo.adicionarContrato(vinculo);
    }

    public void encerrarContratoCargo(Contrato contrato, Integer idCargo, LocalDate data) {
        Cargo cargo = cargoService.buscarCargo(idCargo);

        VinculoContratoCargo vinculo = vinculoContratoCargoRepository.findByContratoIdContratoAndCargoIdCargo(contrato.getIdContrato(), idCargo)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Vinculo entre Contrato com Id "+contrato.getIdContrato()+" e Cargo com Id "+idCargo+" não encontrado."));

        vinculo.setDataFim(data);
    }

    public void removerContratoCargo(Contrato contrato, Integer idCargo) {
        Cargo cargo = cargoService.buscarCargo(idCargo);

        VinculoContratoCargo vinculo = vinculoContratoCargoRepository.findByContratoIdContratoAndCargoIdCargo(contrato.getIdContrato(), idCargo)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Vinculo entre Contrato com Id "+contrato.getIdContrato()+" e Cargo com Id "+idCargo+" não encontrado."));

        contrato.removerCargo(vinculo);
        cargo.removerContrato(vinculo);
    }
}
