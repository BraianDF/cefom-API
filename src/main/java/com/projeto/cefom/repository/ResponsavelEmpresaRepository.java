package com.projeto.cefom.repository;

import com.projeto.cefom.model.ResponsavelEmpresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponsavelEmpresaRepository extends JpaRepository<ResponsavelEmpresa, Integer> {
    Page<ResponsavelEmpresa> findByEmpresaIdEmpresaOrderByDataInicioDesc(Integer idEmpresa, Pageable pageable);
}
