package com.projeto.sistema.repository;

import com.projeto.sistema.model.Empresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
    Page<Empresa> findByApelidoContainingIgnoreCase(String nome, Pageable pageable);
}
