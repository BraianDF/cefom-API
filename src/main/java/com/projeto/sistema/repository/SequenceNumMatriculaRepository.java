package com.projeto.sistema.repository;

import com.projeto.sistema.model.SequenceNumMatricula;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SequenceNumMatriculaRepository extends JpaRepository<SequenceNumMatricula, String> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from SequenceNumMatricula s where s.nome = :nome")
    Optional<SequenceNumMatricula> findByNomeForUpdate(@Param("nome") String nome);
}
