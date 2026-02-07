package com.projeto.cefom.novos.repository;

import com.projeto.cefom.novos.model.SalaAula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaAulaRepository extends JpaRepository<SalaAula, Integer> {
}
