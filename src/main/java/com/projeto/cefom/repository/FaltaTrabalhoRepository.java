package com.projeto.cefom.repository;

import com.projeto.cefom.model.FaltaTrabalho;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FaltaTrabalhoRepository extends JpaRepository<FaltaTrabalho, Integer> {
    boolean existsByContratoIdContratoAndDataInicio(Integer idContrato, LocalDate dataInicio);
    boolean existsByContratoIdContratoAndDataInicioAndIdFaltaTrabalhoNot(Integer idContrato, LocalDate dataInicio, Integer idFaltaTrabalho);
    Page<FaltaTrabalho> findAllByContratoIdContrato(Integer idContrato, Pageable pageable);
    List<FaltaTrabalho> findByContratoMatriculaIdMatriculaAndAndDataInicioBetween(Integer idMatricula, LocalDate dataInicio, LocalDate dataFim);
}
