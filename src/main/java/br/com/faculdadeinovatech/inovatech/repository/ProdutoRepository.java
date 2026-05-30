package br.com.faculdadeinovatech.inovatech.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.faculdadeinovatech.inovatech.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    Page<Produto> findByDescricaoProdutoContainingIgnoreCase(String descricaoProduto, Pageable pageable);

    Page<Produto> findByIdCategoria(Integer idCategoria, Pageable pageable);

    // Filtro da vitrine pública por categoria (navega a associação Produto -> Categoria)
    Page<Produto> findByCategoria_IdCategoria(Integer idCategoria, Pageable pageable);
}
