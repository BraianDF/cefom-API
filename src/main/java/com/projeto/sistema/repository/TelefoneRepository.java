package com.projeto.sistema.repository;

import com.projeto.sistema.model.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Integer> {
    List<Telefone> findByAdolescenteIdAdolescenteOrderByDataInicioDesc(Integer idAdolescente);
    List<Telefone> findByEmpresaIdEmpresaOrderByDataInicioDesc(Integer idAdolescente);
}
