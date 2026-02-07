package com.projeto.cefom.novos.repository;

import com.projeto.cefom.novos.model.JustificativaFalta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JustificativaFaltaRepository extends JpaRepository<JustificativaFalta, Integer> {
}
