package com.projeto.sistema.repository;

import com.projeto.sistema.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {
    boolean existsByProtocoloAprovacao(String protocoloAprovacao);
    boolean existsByProtocoloAprovacaoAndIdCursoNot(String protocoloAprovacao, Integer idCurso);
}
