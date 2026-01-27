package com.projeto.sistema.repository;

import com.projeto.sistema.model.Endereco;
import com.projeto.sistema.model.Familiar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamiliarRepository extends JpaRepository<Familiar, Integer> {
    List<Familiar> findByAdolescenteIdAdolescenteOrderByDataInicioDesc(Integer idAdolescente);
}
