package com.projeto.cefom.novos.repository;

import com.projeto.cefom.novos.model.Aula;
import com.projeto.cefom.novos.model.Disciplina;
import com.projeto.cefom.novos.model.Lecionamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface AulaRepository extends JpaRepository<Aula, Integer> {
    @Query("""
    SELECT COUNT(a) > 0
    FROM Aula a
    WHERE a.dataInicio = :data
      AND a.lecionamento.professor.idProfessor = :idProfessor
      AND :horarioInicio < a.horarioFim
      AND :horarioFim > a.horarioInicio
""")
    boolean existsConflitoHorarioProfessor(
            LocalDate data,
            LocalTime horarioInicio,
            LocalTime horarioFim,
            Integer idProfessor
    );

    @Query("""
    SELECT COUNT(a) > 0
    FROM Aula a
    WHERE a.dataInicio = :data
      AND a.lecionamento.professor.idProfessor = :idProfessor
      AND a.idAula <> :idAula
      AND :horarioInicio < a.horarioFim
      AND :horarioFim > a.horarioInicio
""")
    boolean existsConflitoHorarioProfessorAndIdAulaNot(
            LocalDate data,
            LocalTime horarioInicio,
            LocalTime horarioFim,
            Integer idProfessor,
            Integer idAula
    );

    @Query("""
    SELECT COUNT(a) > 0
    FROM Aula a
    WHERE a.dataInicio = :data
      AND a.sala.idSalaAula = :idSalaAula
      AND :horarioInicio < a.horarioFim
      AND :horarioFim > a.horarioInicio
""")
    boolean existsConflitoHorarioSalaAula(
            LocalDate data,
            LocalTime horarioInicio,
            LocalTime horarioFim,
            Integer idSalaAula
    );

    @Query("""
    SELECT COUNT(a) > 0
    FROM Aula a
    WHERE a.dataInicio = :data
      AND a.sala.idSalaAula = :idSalaAula
      AND a.idAula <> :idAula
      AND :horarioInicio < a.horarioFim
      AND :horarioFim > a.horarioInicio
""")
    boolean existsConflitoHorarioSalaAulaAndIdAulaNot(
            LocalDate data,
            LocalTime horarioInicio,
            LocalTime horarioFim,
            Integer idSalaAula,
            Integer idAula
    );

    @Query("""
    SELECT COUNT(a) > 0
    FROM Aula a
    WHERE a.dataInicio = :data
      AND a.turma.idTurma = :idTurma
      AND :horarioInicio < a.horarioFim
      AND :horarioFim > a.horarioInicio
""")
    boolean existsConflitoHorarioTurma(
            LocalDate data,
            LocalTime horarioInicio,
            LocalTime horarioFim,
            Integer idTurma
    );

    @Query("""
    SELECT COUNT(a) > 0
    FROM Aula a
    WHERE a.dataInicio = :data
      AND a.turma.idTurma = :idTurma
      AND a.idAula <> :idAula
      AND :horarioInicio < a.horarioFim
      AND :horarioFim > a.horarioInicio
""")
    boolean existsConflitoHorarioTurmaAndIdAulaNot(
            LocalDate data,
            LocalTime horarioInicio,
            LocalTime horarioFim,
            Integer idTurma,
            Integer idAula
    );

    Page<Aula> findByLecionamentoDisciplinaNomeContainingIgnoreCase(String nome, Pageable pageable);

    boolean existsByLecionamentoAndIdAulaNot(Lecionamento lecionamento, Integer idAula);
}
