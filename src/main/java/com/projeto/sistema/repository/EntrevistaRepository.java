package com.projeto.sistema.repository;

import com.projeto.sistema.model.Entrevista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface EntrevistaRepository extends JpaRepository<Entrevista, Integer> {
    boolean existsByDataInicioAndEmpresaIdEmpresa(LocalDate dataInicio, Integer idEmpresa);
    boolean existsByDataInicioAndEmpresaIdEmpresaAndIdEntrevistaNot(LocalDate dataInicio, Integer idEmpresa, Integer idEntrevista);
}
