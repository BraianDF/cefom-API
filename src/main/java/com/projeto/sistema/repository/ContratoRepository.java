package com.projeto.sistema.repository;

import com.projeto.sistema.model.Contrato;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Integer> {
    Page<Contrato> findAllByMatriculaAdolescenteIdAdolescente(Integer idAdolescente, Pageable pageable);
}
