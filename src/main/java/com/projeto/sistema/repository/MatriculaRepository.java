package com.projeto.sistema.repository;

import com.projeto.sistema.model.Inscricao;
import com.projeto.sistema.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Integer> {

    boolean existsByAdolescenteIdAdolescenteAndDataFimIsNull(Integer idAdolescente);

    boolean existsByNumMatriculaAndIdMatriculaNot(Integer numMatricula, Integer idMatricula);

    Optional<Matricula> findTopByAdolescenteIdAdolescenteOrderByDataInicioDesc(Integer idAdolescente);

    List<Matricula> findByAdolescenteIdAdolescenteOrderByDataInicioDesc(Integer idAdolescente);
}
