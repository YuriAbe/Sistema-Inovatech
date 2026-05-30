package br.com.faculdadeinovatech.inovatech.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idProduto;

    @Column(nullable = false, length = 40)
    private String descricaoProduto;

    @Column(nullable = false)
    private double valorProduto;

    @Column(nullable = false, length = 30)
    private String marcaProduto;

    // Isso representa o lado "1" do relacionamento com ItemDoProduto
    @OneToMany(mappedBy = "produto")
    private List<ItemDoPedido> itens;

    @ManyToOne // Muito Produtos para uma categoria
    @JoinColumn(name = "idCategoria_fk")
    private Categoria categoria;    

}
