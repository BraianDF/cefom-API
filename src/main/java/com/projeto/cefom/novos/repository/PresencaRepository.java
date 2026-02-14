package com.projeto.cefom.novos.repository;

import com.projeto.cefom.novos.model.Presenca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PresencaRepository extends JpaRepository<Presenca, Integer> {
    Optional<Presenca> findByAulaIdAulaAndAlunoIdMatricula(Integer idAula, Integer idMatricula);
    boolean existsByAulaIdAulaAndAlunoIdMatricula(Integer idAula, Integer idMatricula);
}
