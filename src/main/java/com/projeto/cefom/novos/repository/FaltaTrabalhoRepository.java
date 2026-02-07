package com.projeto.cefom.novos.repository;

import com.projeto.cefom.novos.model.FaltaTrabalho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaltaTrabalhoRepository extends JpaRepository<FaltaTrabalho, Integer> {
}
