package com.projeto.cefom.repository;

import com.projeto.cefom.model.TaxaAdministrativa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxaAdministrativaRepository extends JpaRepository<TaxaAdministrativa, Integer> {
    Page<TaxaAdministrativa> findByEmpresaIdEmpresaOrderByDataInicioDesc(Integer idEmpresa, Pageable pageable);
}
