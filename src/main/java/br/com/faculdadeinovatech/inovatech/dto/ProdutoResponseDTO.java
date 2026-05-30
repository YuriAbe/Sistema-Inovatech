package br.com.faculdadeinovatech.inovatech.dto;

import br.com.faculdadeinovatech.inovatech.entity.Produto;

public record ProdutoResponseDTO(
        Integer idProduto,
        String descricaoProduto,
        Double valorProduto,
        String marcaProduto,
        CategoriaResponseDTO categoria // Retornamos o DTO da categoria, não a Entity
) {
    // Método utilitário para converter de Entity para DTO
    public static ProdutoResponseDTO fromEntity(Produto produto) {
        return new ProdutoResponseDTO(
                produto.getIdProduto(),
                produto.getDescricaoProduto(),
                produto.getValorProduto(),
                produto.getMarcaProduto(),
                CategoriaResponseDTO.fromEntity(produto.getCategoria())
        );
    }
}