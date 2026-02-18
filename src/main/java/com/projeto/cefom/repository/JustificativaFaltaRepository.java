package com.projeto.cefom.repository;

import com.projeto.cefom.enums.MotivoJustificativa;
import com.projeto.cefom.model.JustificativaFalta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface JustificativaFaltaRepository extends JpaRepository<JustificativaFalta, Integer> {
    boolean existsByAlunoIdMatriculaAndMotivoAndDataInicioAndQtdeDias(Integer idMatricula, MotivoJustificativa motivo, LocalDate dataInicio, Integer qtdeDias);
    boolean existsByAlunoIdMatriculaAndMotivoAndDataInicioAndQtdeDiasAndIdJustificativaFaltaNot(Integer idMatricula, MotivoJustificativa motivo, LocalDate dataInicio, Integer qtdeDias, Integer idJustificativaFalta);
    Page<JustificativaFalta> findByAlunoAdolescenteIdAdolescente(Integer idAdolescente, Pageable pageable);
    List<JustificativaFalta> findByAlunoIdMatriculaAndDataInicioLessThanEqualAndDataFimGreaterThanEqual(Integer idMatricula, LocalDate dataInicio, LocalDate dataFim);
}
