package com.projeto.cefom.novos.repository;

import com.projeto.cefom.novos.model.Turma;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Integer> {
    boolean existsByNome(String nome);
    boolean existsByNomeAndIdTurmaNot(String nome, Integer idTurma);
    Page<Turma> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
