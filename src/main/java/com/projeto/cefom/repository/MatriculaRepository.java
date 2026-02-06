package com.projeto.cefom.repository;

import com.projeto.cefom.model.Matricula;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Integer> {

    boolean existsByAdolescenteIdAdolescenteAndDataFimIsNull(Integer idAdolescente);

    boolean existsByNumMatriculaAndIdMatriculaNot(Integer numMatricula, Integer idMatricula);

    Optional<Matricula> findTopByAdolescenteIdAdolescenteOrderByDataInicioDesc(Integer idAdolescente);

    Page<Matricula> findByAdolescenteIdAdolescenteOrderByDataInicioDesc(Integer idAdolescente, Pageable pageable);
}
