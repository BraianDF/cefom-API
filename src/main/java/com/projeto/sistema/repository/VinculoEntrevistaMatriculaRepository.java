package com.projeto.sistema.repository;

import com.projeto.sistema.model.VinculoEntrevistaMatricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VinculoEntrevistaMatriculaRepository extends JpaRepository<VinculoEntrevistaMatricula, Integer> {
    List<VinculoEntrevistaMatricula> findByEntrevistaIdEntrevista(Integer idEntrevista);
    List<VinculoEntrevistaMatricula> findByMatriculaIdMatricula(Integer idMatricula);
    Optional<VinculoEntrevistaMatricula> findByEntrevistaIdEntrevistaAndMatriculaIdMatricula(Integer idEntrevista, Integer idMatricula);
    boolean existsByEntrevistaIdEntrevistaAndMatriculaIdMatricula(Integer idEntrevista, Integer idMatricula);
}
