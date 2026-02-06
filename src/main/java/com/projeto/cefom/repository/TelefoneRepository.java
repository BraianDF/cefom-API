package com.projeto.cefom.repository;

import com.projeto.cefom.model.Telefone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Integer> {
    Page<Telefone> findByAdolescenteIdAdolescenteOrderByDataInicioDesc(Integer idAdolescente, Pageable pageable);
    Page<Telefone> findByEmpresaIdEmpresaOrderByDataInicioDesc(Integer idEmpresa, Pageable pageable);
}
