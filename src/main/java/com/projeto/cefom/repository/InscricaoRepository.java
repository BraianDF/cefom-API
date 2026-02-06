package com.projeto.cefom.repository;

import com.projeto.cefom.model.Inscricao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, Integer> {
    boolean existsByAdolescenteIdAdolescenteAndDataFimIsNull(Integer idAdolescente);

    Optional<Inscricao> findTopByAdolescenteIdAdolescenteOrderByDataInicioDesc(Integer idAdolescente);

    Page<Inscricao> findByAdolescenteIdAdolescenteOrderByDataInicioDesc(Integer idAdolescente, Pageable pageable);

}
