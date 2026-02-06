package com.projeto.cefom.repository;

import com.projeto.cefom.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Integer> {

    boolean existsByCpf(String cpf);

    Optional<Documento> findByCpf(String cpf);

    Optional<Documento> findByCnpj(String cnpj);

    Optional<Documento> findByAdolescenteIdAdolescente(Integer idAdolescente);
}
