package com.projeto.cefom.repository;

import com.projeto.cefom.model.Lecionamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LecionamentoRepository extends JpaRepository<Lecionamento, Integer> {
    Lecionamento findLecionamentoByProfessorIdProfessorAndDisciplinaIdDisciplina(Integer idProfessor, Integer idDisciplina);
    boolean existsByProfessorIdProfessorAndDisciplinaIdDisciplina(Integer idProfessor, Integer idDisciplina);
}
