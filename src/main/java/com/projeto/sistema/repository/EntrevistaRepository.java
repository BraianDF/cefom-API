package com.projeto.sistema.repository;

import com.projeto.sistema.model.Empresa;
import com.projeto.sistema.model.Entrevista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface EntrevistaRepository extends JpaRepository<Entrevista, Integer> {
    boolean existsByDataInicioAndEmpresaIdEmpresa(LocalDate dataInicio, Integer idEmpresa);
    boolean existsByDataInicioAndEmpresaIdEmpresaAndIdEntrevistaNot(LocalDate dataInicio, Integer idEmpresa, Integer idEntrevista);
    @Query(
            value = """
        SELECT e
        FROM Entrevista e
        JOIN e.empresa emp
        WHERE LOWER(emp.apelido) LIKE LOWER(CONCAT('%', :nome, '%'))
    """,
            countQuery = """
        SELECT COUNT(e)
        FROM Entrevista e
        JOIN e.empresa emp
        WHERE LOWER(emp.apelido) LIKE LOWER(CONCAT('%', :nome, '%'))
    """
    )
    Page<Entrevista> buscarPorEmpresaApelido(
            @Param("nome") String nome,
            Pageable pageable
    );
}
