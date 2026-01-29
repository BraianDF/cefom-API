package com.projeto.sistema.repository;

import com.projeto.sistema.model.DadosSocial;
import com.projeto.sistema.model.Escolaridade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DadosSocialRepository extends JpaRepository<DadosSocial, Integer> {
    Page<DadosSocial> findByAdolescenteIdAdolescenteOrderByDataInicioDesc(Integer idAdolescente, Pageable pageable);
}
