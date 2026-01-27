package com.projeto.sistema.repository;

import com.projeto.sistema.model.ResponsavelEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResponsavelEmpresaRepository extends JpaRepository<ResponsavelEmpresa, Integer> {
    List<ResponsavelEmpresa> findByEmpresaIdEmpresaOrderByDataInicioDesc(Integer idEmpresa);
}
