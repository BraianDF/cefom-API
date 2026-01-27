package com.projeto.sistema.repository;

import com.projeto.sistema.model.SequenceNumInscricao;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SequenceNumInscricaoRepository extends JpaRepository<SequenceNumInscricao, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from SequenceNumInscricao s where s.nome = :nome")
    Optional<SequenceNumInscricao> findByNomeForUpdate(@Param("nome") String nome);
}
