package com.projeto.sistema.repository;

import com.projeto.sistema.model.Afastamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AfastamentoRepository extends JpaRepository<Afastamento, Integer> {
    List<Afastamento> findAllByContratoIdContrato(Integer idContrato);
    boolean existsByDataInicioAndDataFim(LocalDate dataInicio, LocalDate dataFim);
    boolean existsByDataInicioAndDataFimAndIdAfastamentoNot(LocalDate dataInicio, LocalDate dataFim, Integer idAfastamento);
}
