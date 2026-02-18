package com.projeto.cefom.repository;

import com.projeto.cefom.model.Professor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
    boolean existsByNome(String nome);
    boolean existsByNomeAndIdProfessorNot(String nome, Integer idProfessor);
    Page<Professor> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
