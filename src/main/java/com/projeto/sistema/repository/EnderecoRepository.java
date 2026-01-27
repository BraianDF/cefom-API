package com.projeto.sistema.repository;

import com.projeto.sistema.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

    List<Endereco> findByAdolescenteIdAdolescenteOrderByDataInicioDesc(Integer idAdolescente);

    List<Endereco> findByEscolaIdEscolaOrderByDataInicioDesc(Integer idEscola);

    List<Endereco> findByEmpresaIdEmpresaOrderByDataInicioDesc(Integer idEmpresa);
}
