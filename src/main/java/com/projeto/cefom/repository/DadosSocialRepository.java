package com.projeto.cefom.repository;

import com.projeto.cefom.model.DadosSocial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DadosSocialRepository extends JpaRepository<DadosSocial, Integer> {
    Page<DadosSocial> findByAdolescenteIdAdolescenteOrderByDataInicioDesc(Integer idAdolescente, Pageable pageable);
}
