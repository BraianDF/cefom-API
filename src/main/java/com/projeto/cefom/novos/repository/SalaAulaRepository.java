package com.projeto.cefom.novos.repository;

import com.projeto.cefom.novos.model.SalaAula;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaAulaRepository extends JpaRepository<SalaAula, Integer> {
    boolean existsByNome(String nome);
    boolean existsByNomeAndIdSalaAulaNot(String nome, Integer idSalaAula);
    Page<SalaAula> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
