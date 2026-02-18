package com.projeto.cefom.image.repository;

import com.projeto.cefom.image.model.FotoAdolescente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FotoAdolescenteRepository extends JpaRepository<FotoAdolescente, Integer> {
    Optional<FotoAdolescente> findByMatriculaIdMatricula(Integer idMatricula);
    Optional<FotoAdolescente> findByInscricaoIdInscricao(Integer idInscricao);
}
