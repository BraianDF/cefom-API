package com.projeto.sistema.repository;

import com.projeto.sistema.model.Inscricao;
import com.projeto.sistema.model.Matricula;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, Integer> {
    boolean existsByAdolescenteIdAdolescenteAndDataFimIsNull(Integer idAdolescente);

    Optional<Inscricao> findTopByAdolescenteIdAdolescenteOrderByDataInicioDesc(Integer idAdolescente);

    Page<Inscricao> findByAdolescenteIdAdolescenteOrderByDataInicioDesc(Integer idAdolescente, Pageable pageable);

}
