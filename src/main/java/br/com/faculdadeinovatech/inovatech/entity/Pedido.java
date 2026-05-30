package br.com.faculdadeinovatech.inovatech.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
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
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idPedido;

    private LocalDate dataPedido;

    private double totalPedido;

    // Relacionamento com o Aluno
    @ManyToOne
    @JoinColumn(name = "idAluno_fk")
    private Aluno aluno;

    // Relacionamento com os Itens
    @JsonManagedReference
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemDoPedido> itens;
    
    // Método para calcular o total
    public double calcularTotal() {
        double total = 0.0;
        if(itens != null) {
            for(ItemDoPedido item : itens) {
                total += item.getSubTotal();
            }
        }
        return total;

    }

    // Método para atualizar o Total
    public void atualizarTotal() {
        this.totalPedido = calcularTotal();
    }
}
