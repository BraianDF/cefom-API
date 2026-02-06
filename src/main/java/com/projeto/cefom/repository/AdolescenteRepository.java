package com.projeto.cefom.repository;

import com.projeto.cefom.dto.base.AdolescenteListarBaseDTO;
import com.projeto.cefom.model.Adolescente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdolescenteRepository extends JpaRepository<Adolescente, Integer> {
    @Query("""
    select new com.projeto.cefom.dto.base.AdolescenteListarBaseDTO(
        a.idAdolescente,
        a.nome,
        d.cpf
    )
    from Adolescente a
    join a.documento d
    order by a.nome
""")
    List<AdolescenteListarBaseDTO> listarBaseParaSelect();
    Page<Adolescente> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
