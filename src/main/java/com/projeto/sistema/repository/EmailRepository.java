package com.projeto.sistema.repository;

import com.projeto.sistema.model.Email;
import com.projeto.sistema.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository<Email, Integer> {
    List<Email> findByAdolescenteIdAdolescenteOrderByDataInicioDesc(Integer idAdolescente);
    List<Email> findByEmpresaIdEmpresaOrderByDataInicioDesc(Integer idEmpresa);
}
