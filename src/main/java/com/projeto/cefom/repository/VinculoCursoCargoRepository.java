package com.projeto.cefom.repository;

import com.projeto.cefom.model.VinculoCursoCargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VinculoCursoCargoRepository extends JpaRepository<VinculoCursoCargo, Integer> {
    List<VinculoCursoCargo> findByCursoIdCurso(Integer idCurso);
    List<VinculoCursoCargo> findByCargoIdCargo(Integer idCargo);
    Optional<VinculoCursoCargo> findByCursoIdCursoAndCargoIdCargo(Integer idCurso, Integer idCargo);
    boolean existsByCursoIdCursoAndCargoIdCargo(Integer idCurso, Integer idCargo);
}
