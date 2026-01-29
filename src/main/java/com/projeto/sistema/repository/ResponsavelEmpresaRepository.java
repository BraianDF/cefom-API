package com.projeto.sistema.repository;

import com.projeto.sistema.model.ResponsavelEmpresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResponsavelEmpresaRepository extends JpaRepository<ResponsavelEmpresa, Integer> {
    Page<ResponsavelEmpresa> findByEmpresaIdEmpresaOrderByDataInicioDesc(Integer idEmpresa, Pageable pageable);
}
