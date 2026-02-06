package com.projeto.cefom.repository;

import com.projeto.cefom.dto.response.EscolaListarResponseDTO;
import com.projeto.cefom.model.Escola;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EscolaRepository extends JpaRepository<Escola, Integer> {
    @Query("select new com.projeto.cefom.dto.response.EscolaListarResponseDTO(e.idEscola, e.nome) from Escola e order by e.nome")
    List<EscolaListarResponseDTO> listarParaSelect();
    Optional<Escola> findByNomeIgnoreCase(String nome);
    boolean existsByNome(String nome);
    boolean existsByNomeAndIdEscolaNot(String nome, Integer idEscola);
    Page<Escola> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
