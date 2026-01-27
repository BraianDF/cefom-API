package com.projeto.sistema.repository;

import com.projeto.sistema.model.Caracteristica;
import com.projeto.sistema.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaracteristicaRepository extends JpaRepository<Caracteristica, Integer> {
    List<Caracteristica> findByAdolescenteIdAdolescenteOrderByDataInicioDesc(Integer idAdolescente);
}
