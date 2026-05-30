package br.com.faculdadeinovatech.inovatech.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProdutoRequestDTO(
        @NotBlank(message = "A descrição é obrigatória")
        @Size(min = 3, message = "A descrição deve ter no mínimo 3 caracteres")
        String descricaoProduto,

        @NotNull(message = "O valor é obrigatório")
        @Positive(message = "O valor deve ser maior que zero")
        Double valorProduto,

        @NotBlank(message = "A marca é obrigatória")
        @Size(min = 3, message = "A marca deve ter no mínimo 3 caracteres")
        String marcaProduto,

        @NotNull Integer idCategoria // Recebemos apenas o ID
) {}
