package com.projeto.sistema.repository;

import com.projeto.sistema.model.Endereco;
import com.projeto.sistema.model.Escolaridade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EscolaridadeRepository extends JpaRepository<Escolaridade, Integer> {
    List<Escolaridade> findByAdolescenteIdAdolescenteOrderByDataInicioDesc(Integer idAdolescente);
}
