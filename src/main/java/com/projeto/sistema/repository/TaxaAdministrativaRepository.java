package com.projeto.sistema.repository;

import com.projeto.sistema.model.TaxaAdministrativa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaxaAdministrativaRepository extends JpaRepository<TaxaAdministrativa, Integer> {
    List<TaxaAdministrativa> findByEmpresaIdEmpresaOrderByDataInicioDesc(Integer idEmpresa);
}
