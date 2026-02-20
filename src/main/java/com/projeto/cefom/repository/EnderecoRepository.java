package com.projeto.cefom.repository;

import com.projeto.cefom.model.Endereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

    Page<Endereco> findByAdolescenteIdAdolescenteOrderByDataInicioDesc(Integer idAdolescente, Pageable pageable);

    Page<Endereco> findByEscolaIdEscolaOrderByDataInicioDesc(Integer idEscola, Pageable pageable);

    Page<Endereco> findByEmpresaIdEmpresaOrderByDataInicioDesc(Integer idEmpresa, Pageable pageable);

    @Query("""
        SELECT e FROM Endereco e
        WHERE UPPER(TRIM(e.cidade)) = UPPER(TRIM(:cidade))
        AND UPPER(TRIM(e.bairro)) = UPPER(TRIM(:bairro))
    """)
    List<Endereco> findByCidadeAndBairro(String cidade, String bairro);

    Integer countByTerritorioIsNull();

    @Query("""
        SELECT DISTINCT e.bairro
        FROM Endereco e
        WHERE e.territorio IS NULL
    """)
    Set<String> listarBairrosSemTerritorio();
}
