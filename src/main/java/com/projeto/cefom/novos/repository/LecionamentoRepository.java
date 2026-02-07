package com.projeto.cefom.novos.repository;

import com.projeto.cefom.novos.model.Lecionamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LecionamentoRepository extends JpaRepository<Lecionamento, Integer> {
}
