package br.com.faculdadeinovatech.inovatech.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.faculdadeinovatech.inovatech.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    Page<Categoria> findByNomeCategoriaContainingIgnoreCase(String nomeCategoria, Pageable pageable);
}
