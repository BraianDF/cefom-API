package com.projeto.cefom.repository;

import com.projeto.cefom.model.Presenca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PresencaRepository extends JpaRepository<Presenca, Integer> {
    Optional<Presenca> findByAulaIdAulaAndAlunoIdMatricula(Integer idAula, Integer idMatricula);
    boolean existsByAulaIdAulaAndAlunoIdMatricula(Integer idAula, Integer idMatricula);
    List<Presenca> findByAlunoIdMatriculaAndAulaDataInicioBetween(Integer idMatricula, LocalDate dataInicio, LocalDate dataFim);
}
