package com.projeto.cefom.repository;

import com.projeto.cefom.model.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email, Integer> {
    Page<Email> findByAdolescenteIdAdolescenteOrderByDataInicioDesc(Integer idAdolescente, Pageable pageable);
    Page<Email> findByEmpresaIdEmpresaOrderByDataInicioDesc(Integer idEmpresa, Pageable pageable);
}
