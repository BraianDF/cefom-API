package com.projeto.cefom.repository;

import com.projeto.cefom.model.Territorio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TerritorioRepository extends JpaRepository<Territorio, Integer> {

    @Query("""
       select t
       from Territorio t
       join t.bairros b
       where upper(b) = upper(:bairro)
    """)
    Optional<Territorio> findByBairro(@Param("bairro") String bairro);

    Optional<Territorio> findByResultado(String resultado);

    boolean existsByResultado(String territorioResultado);
    boolean existsByResultadoAndIdTerritorioNot(String territorioResultado, Integer idTerritorio);
    Page<Territorio> findByResultadoContainingIgnoreCase(String nome, Pageable pageable);

}
