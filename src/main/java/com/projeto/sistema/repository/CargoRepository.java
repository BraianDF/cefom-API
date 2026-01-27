package com.projeto.sistema.repository;

import com.projeto.sistema.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Integer> {
    boolean existsByFuncao(String funcao);
    boolean existsByFuncaoAndIdCargoNot(String funcao, Integer idCargo);
}
