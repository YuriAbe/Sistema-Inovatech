package br.com.faculdadeinovatech.inovatech.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.faculdadeinovatech.inovatech.dto.ProdutoRequestDTO;
import br.com.faculdadeinovatech.inovatech.dto.ProdutoResponseDTO;
import br.com.faculdadeinovatech.inovatech.entity.Categoria;
import br.com.faculdadeinovatech.inovatech.entity.Produto;
import br.com.faculdadeinovatech.inovatech.repository.CategoriaRepository;
import br.com.faculdadeinovatech.inovatech.repository.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public ProdutoResponseDTO save(ProdutoRequestDTO dto) {

        Categoria categoria = categoriaRepository.findById(dto.idCategoria())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada!"));

        Produto produto = new Produto();
        produto.setDescricaoProduto(dto.descricaoProduto());
        produto.setValorProduto(dto.valorProduto());
        produto.setMarcaProduto(dto.marcaProduto());
        produto.setCategoria(categoria); // Vincula o objeto completo

        Produto salvo = produtoRepository.save(produto);
        return ProdutoResponseDTO.fromEntity(salvo);
    }

    public ProdutoResponseDTO update(Integer id, ProdutoRequestDTO dto) {
        Produto existente = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        existente.setDescricaoProduto(dto.descricaoProduto());
        existente.setValorProduto(dto.valorProduto());
        existente.setMarcaProduto(dto.marcaProduto());

        Produto atualizado = produtoRepository.save(existente);
        return ProdutoResponseDTO.fromEntity(atualizado);
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

    // Métodos de busca personalizada
    public Page<ProdutoResponseDTO> buscarPorDescricao(String descricaoProduto, Pageable paginacao) {
        return produtoRepository.findByDescricaoProdutoContainingIgnoreCase(descricaoProduto, paginacao)
                .map(ProdutoResponseDTO::fromEntity);
    }

    public Page<ProdutoResponseDTO> listarPorCategoria(Integer idCategoria, Pageable pageable) {
        return produtoRepository.findByIdCategoria(idCategoria, pageable)
                .map(ProdutoResponseDTO::fromEntity);
    }

}
