package br.com.faculdadeinovatech.inovatech.dto;

import br.com.faculdadeinovatech.inovatech.entity.Categoria;

public record CategoriaResponseDTO(Integer idCategoria, String nomeCategoria) {
    public static CategoriaResponseDTO fromEntity(Categoria categoria) {
        return new CategoriaResponseDTO(categoria.getIdCategoria(), categoria.getNomeCategoria());
    }
}