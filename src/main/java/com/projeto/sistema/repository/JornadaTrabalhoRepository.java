package com.projeto.sistema.repository;

import com.projeto.sistema.model.JornadaTrabalho;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JornadaTrabalhoRepository extends JpaRepository<JornadaTrabalho, Integer> {
    Page<JornadaTrabalho> findAllByContratoIdContrato(Integer idContrato, Pageable pageable);
}
