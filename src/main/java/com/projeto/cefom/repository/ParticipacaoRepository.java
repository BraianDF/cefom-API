package com.projeto.cefom.repository;

import com.projeto.cefom.model.Participacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipacaoRepository extends JpaRepository<Participacao, Integer> {
    List<Participacao> findByTurmaIdTurmaAndAlunoIdMatricula(Integer idTurma, Integer idMatricula);
    Optional<Participacao> findByTurmaIdTurmaAndAlunoIdMatriculaAndDataFimIsNull(Integer idTurma, Integer idMatricula);
}
