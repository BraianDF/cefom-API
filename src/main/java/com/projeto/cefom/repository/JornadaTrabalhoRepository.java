package com.projeto.cefom.repository;

import com.projeto.cefom.model.JornadaTrabalho;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JornadaTrabalhoRepository extends JpaRepository<JornadaTrabalho, Integer> {
    Page<JornadaTrabalho> findAllByContratoIdContrato(Integer idContrato, Pageable pageable);
}
