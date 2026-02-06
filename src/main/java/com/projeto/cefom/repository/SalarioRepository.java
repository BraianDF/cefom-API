package com.projeto.cefom.repository;

import com.projeto.cefom.model.Salario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalarioRepository extends JpaRepository<Salario, Integer> {
    Page<Salario> findAllByContratoIdContrato(Integer idContrato, Pageable pageable);
}
