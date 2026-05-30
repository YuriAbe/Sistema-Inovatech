package br.com.faculdadeinovatech.inovatech.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.faculdadeinovatech.inovatech.dto.ProdutoResponseDTO;
import br.com.faculdadeinovatech.inovatech.entity.Produto;
import br.com.faculdadeinovatech.inovatech.repository.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto salvarEntity(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto buscarEntity(Integer id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID " + id));
    }

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    // Listar todos os produtos com paginação
    public Page<ProdutoResponseDTO> listarPaginado(Pageable paginacao) {
        return produtoRepository.findAll(paginacao)
                .map(ProdutoResponseDTO::fromEntity);
    }

    public Optional<ProdutoResponseDTO> findById(Integer id) {
        return produtoRepository.findById(id)
                .map(ProdutoResponseDTO::fromEntity);
    }

    public void deleteById(Integer id) {
        if (!produtoRepository.existsById(id)) {
            throw new RuntimeException("Não é possível deletar: Produto não encontrado com ID " + id);
        }
        produtoRepository.deleteById(id);
    }

    // Busca da vitrine pública: combina texto e categoria com paginação/ordenação
    public Page<ProdutoResponseDTO> buscarVitrine(String q, Integer idCategoria, Pageable pageable) {
        if (q != null && !q.isBlank()) {
            return produtoRepository.findByDescricaoProdutoContainingIgnoreCase(q.trim(), pageable)
                    .map(ProdutoResponseDTO::fromEntity);
        }
        if (idCategoria != null) {
            return produtoRepository.findByCategoria_IdCategoria(idCategoria, pageable)
                    .map(ProdutoResponseDTO::fromEntity);
        }
        return produtoRepository.findAll(pageable).map(ProdutoResponseDTO::fromEntity);
    }

}
