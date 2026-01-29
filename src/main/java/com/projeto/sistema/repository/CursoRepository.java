package com.projeto.sistema.repository;

import com.projeto.sistema.model.Curso;
import com.projeto.sistema.model.Empresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {
    boolean existsByProtocoloAprovacao(String protocoloAprovacao);
    boolean existsByProtocoloAprovacaoAndIdCursoNot(String protocoloAprovacao, Integer idCurso);
    Page<Curso> findByNomeCursoContainingIgnoreCase(String nome, Pageable pageable);
}
