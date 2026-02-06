package com.projeto.cefom.repository;

import com.projeto.cefom.model.Escolaridade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EscolaridadeRepository extends JpaRepository<Escolaridade, Integer> {
    Page<Escolaridade> findByAdolescenteIdAdolescenteOrderByDataInicioDesc(Integer idAdolescente, Pageable pageable);
}
