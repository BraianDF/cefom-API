package com.projeto.sistema.repository;

import com.projeto.sistema.model.VinculoContratoCargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VinculoContratoCargoRepository extends JpaRepository<VinculoContratoCargo, Integer> {
    List<VinculoContratoCargo> findByContratoIdContrato(Integer idContrato);
    List<VinculoContratoCargo> findByCargoIdCargo(Integer idCargo);
    Optional<VinculoContratoCargo> findByContratoIdContratoAndCargoIdCargo(Integer idContrato, Integer idCargo);
    boolean existsByContratoIdContratoAndCargoIdCargo(Integer idContrato, Integer idCargo);
}
