package com.projeto.cefom.novos.repository;

import com.projeto.cefom.novos.model.Disciplina;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Integer> {
    boolean existsByNome(String nome);
    boolean existsByNomeAndIdDisciplinaNot(String nome, Integer idDisciplina);
    Page<Disciplina> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
