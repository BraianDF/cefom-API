package com.projeto.cefom.repository;

import com.projeto.cefom.model.Familiar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamiliarRepository extends JpaRepository<Familiar, Integer> {
    Page<Familiar> findByAdolescenteIdAdolescenteOrderByDataInicioDesc(Integer idAdolescente, Pageable pageable);
}
