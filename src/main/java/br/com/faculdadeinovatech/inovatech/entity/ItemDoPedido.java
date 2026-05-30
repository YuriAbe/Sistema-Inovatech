package br.com.faculdadeinovatech.inovatech.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ItemDoPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idItem;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "idPedido_fk")
    private Pedido pedido;
    
    @ManyToOne
    @JoinColumn(name = "idProduto_fk")
    private Produto produto;

    private Integer quantidade;

    private double preco;

    private double subTotal;

    // Método para calcular subtotal
    public double calcularSubtotal() {
        return quantidade * preco;
    }
    
    // Método para atualizar subtotal
    public void atualizarSubTotal () {
        this.subTotal = calcularSubtotal();
    }
}
