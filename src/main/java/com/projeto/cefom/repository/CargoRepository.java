package com.projeto.cefom.repository;

import com.projeto.cefom.model.Cargo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Integer> {
    boolean existsByFuncao(String funcao);
    boolean existsByFuncaoAndIdCargoNot(String funcao, Integer idCargo);
    Page<Cargo> findByFuncaoContainingIgnoreCase(String nome, Pageable pageable);
}
