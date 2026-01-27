package com.projeto.sistema.repository;

import com.projeto.sistema.model.Salario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalarioRepository extends JpaRepository<Salario, Integer> {
    List<Salario> findAllByContratoIdContrato(Integer idContrato);
}
